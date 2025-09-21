package at.tfro.sonic_link.shared_client.player.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.tfro.sonic_link.core.SystemAppDirectories
import at.tfro.sonic_link.core.logger.Log
import at.tfro.sonic_link.core.media.MediaPlayer
import at.tfro.sonic_link.core.mediaDir
import at.tfro.sonic_link.shared_client.core.data.database.media.AssetEntity
import at.tfro.sonic_link.shared_client.core.data.database.media.AssetType
import at.tfro.sonic_link.shared_client.core.data.database.media.MediaDao
import at.tfro.sonic_link.shared_client.core.data.database.media.MediaEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.io.files.FileNotFoundException
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.Uuid

class PlayerViewModel(
    private val dirs: SystemAppDirectories,
    private val dao: MediaDao,
    private val mediaPlayer: MediaPlayer,
) : ViewModel() {
    companion object {
        private const val TAG = "PlayerViewModel"
    }

    private var _state = MutableStateFlow(PlayerState())
    val state: StateFlow<PlayerState>
        get() = _state
            .onStart {}
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = _state.value
            )

    fun onAction(action: PlayerAction) {
        when (action) {
            PlayerAction.IndexMedia -> indexMedia()

            is PlayerAction.SeekTo -> TODO()
            is PlayerAction.SetVolume -> TODO()
            PlayerAction.Random -> {
                viewModelScope.launch {
                    Log.tag(TAG).d { "Skipping to next media..." }

                    val current = _state.value.currentModel
                    val next = dao.getRandomMedia(
                        exclude = current?.mediaWithAssets?.media?.uuid?.let { listOf(it) }
                            ?: emptyList()
                    )
                    if (next == null) {
                        Log.tag(TAG).d { "No next media found." }
                        return@launch
                    }

                    _state.update {
                        it.copy(currentModel = Record(mediaWithAssets = next))
                    }

                    mediaPlayer.playAsset(
                        next.assets.firstOrNull { asset -> asset.type == AssetType.AUDIO }?.path
                            ?: return@launch
                    )
                }
            }

            PlayerAction.SkipPrevious -> {
                _state.update {
                    val prev = it.history?.lastOrNull()
                    if (prev != null) {
                        it.copy(
                            currentModel = prev,
                            history = it.history.dropLast(1),
                            queue = listOf(it.currentModel!!) + (it.queue ?: emptyList())
                        )
                    } else {
                        it
                    }
                }

                mediaPlayer.skipToPrevious()
            }

            PlayerAction.SkipNext -> {
                _state.update {
                    val nextInQueue = it.queue?.firstOrNull()
                    if (nextInQueue != null) {
                        it.copy(
                            currentModel = nextInQueue,
                            queue = it.queue.drop(1)
                        )
                    } else {
                        it
                    }
                }

                mediaPlayer.skipToNext()
            }

            PlayerAction.TogglePlay -> {
                viewModelScope.launch {
                    val isPlaying = _state.value.isPlaying

                    _state.update {
                        it.copy(isPlaying = !isPlaying)
                    }

                    if (isPlaying) {
                        mediaPlayer.pause()
                    } else {
                        mediaPlayer.play()
                    }
                }
            }

            PlayerAction.ShowQueue -> {

            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun indexMedia() {
        viewModelScope.launch {
            setupTestData()


            val dir = dirs.mediaDir()
            SystemFileSystem.createDirectories(path = dir, mustCreate = false)

            Log.tag(TAG).d { "Dropping old indexes" }
            dao.deleteAssets()

            val now = Clock.System.now()
            Log.tag(TAG).d { "Indexing media files..." }

            dao.getAllMedia()
                .flatMap { media ->
                    val assetDir = Path(dir, media.uuid.toHexDashString())
                    Log.tag(TAG).d { "Searching assets in directory: $assetDir" }

                    val assetsRaw = try {
                        SystemFileSystem.list(assetDir)
                    } catch (_: FileNotFoundException) {
                        Log.tag(TAG).e { "Media directory not found: $assetDir" }
                        emptyList()
                    }

                    val assets = assetsRaw
                        .filter { SystemFileSystem.metadataOrNull(it)?.isRegularFile == true }
                        .map { file ->
                            val asset = AssetEntity(
                                uuid = Uuid.random(),
                                mediaUuid = media.uuid,
                                path = file,
                                type = AssetType.fromFileExtension(file),
                            )

                            Log.tag(TAG).d { "Found asset: $asset" }
                            asset
                        }

                    assets
                }
                .chunked(100)
                .forEach { chunk ->
                    Log.tag(TAG).d { "Inserting chunk of ${chunk.size} assets..." }
                    dao.insertAssets(*chunk.toTypedArray())
                }

            val duration = Clock.System.now() - now
            Log.tag(TAG)
                .d { "Media indexing completed after ${duration.inWholeMilliseconds}ms." }

            addAllToQueue()
        }
    }


    private suspend fun setupTestData() {
        val media1 = MediaEntity(
            uuid = Uuid.parseHexDash("00000000-0000-0000-0000-000000000001"),
            title = "Scream Until You Wake",
            artist = "Avatar",
            album = "Hunter Gatherer",
        )

        val media2 = MediaEntity(
            uuid = Uuid.parseHexDash("00000000-0000-0000-0000-000000000002"),
            title = "The Outsider",
            artist = "A Perfect Circle",
            album = "Thirteenth Step",
        )

        dao.deleteMedia()
        dao.insertMedia(media1, media2)

        dao.getAllMedia().forEach { media ->
            Log.tag(TAG).d { "Media in DB: $media" }
        }
    }

    private suspend fun addAllToQueue() {
        Log.tag(TAG).d { "Creating playlist with all media" }
        val assets: List<AssetEntity> = dao.getAllMediaWithAssets().onEach {
            Log.tag(TAG).w { "Media for playlist: ${it.media}, assets: ${it.assets}" }
        }
            .mapNotNull { it.assets.find { asset -> asset.type == AssetType.AUDIO } }

        Log.tag(TAG).d { "Found ${assets.size} audio assets for playlist" }
        mediaPlayer.addItemsToQueue(assets.map { it.path })
    }
}