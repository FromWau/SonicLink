package at.tfro.sonic_link.core.media

import kotlinx.io.files.Path

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING", "Unused")
expect class MediaPlayer {
    fun play()
    fun playAsset(path: Path)
    fun pause()
    fun skipToNext()
    fun skipToPrevious()
    fun isPlaying(): Boolean

    fun addItemsToQueue(paths: List<Path>)
}