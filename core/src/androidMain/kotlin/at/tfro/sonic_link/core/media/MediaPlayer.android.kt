package at.tfro.sonic_link.core.media

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import at.tfro.sonic_link.core.logger.Log
import kotlinx.io.files.Path
import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class MediaPlayer(context: Context) {
    companion object {
        private const val TAG = "MediaPlayer"
    }

    val player = ExoPlayer.Builder(context).build()

    actual fun play() {
        Log.tag(TAG).d { "Play" }
        player.playWhenReady = true
    }

    actual fun playAsset(path: Path) {
        Log.tag(TAG).d { "Play asset: $path" }
        val mediaItem = MediaItem.fromUri(Uri.fromFile(File(path.toString())))

        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
    }

    actual fun pause() {
        Log.tag(TAG).d { "Pause" }
        player.playWhenReady = false
    }

    actual fun addItemsToQueue(paths: List<Path>) {
        Log.tag(TAG).d { "Add items to queue: $paths" }
        val mediaItems = paths.map { path ->
            MediaItem.fromUri(Uri.fromFile(File(path.toString())))
        }
        player.addMediaItems(mediaItems)
        player.prepare()
    }

    actual fun skipToNext() {
        Log.tag(TAG).d { "Skip to next" }
        player.seekToNextMediaItem()
    }

    actual fun skipToPrevious() {
        Log.tag(TAG).d { "Skip to previous" }
        player.seekToPreviousMediaItem()
    }

    actual fun isPlaying(): Boolean {
        return player.isPlaying
    }
}
