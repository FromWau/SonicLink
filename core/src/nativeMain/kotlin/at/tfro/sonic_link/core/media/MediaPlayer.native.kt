package at.tfro.sonic_link.core.media

import kotlinx.io.files.Path

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class MediaPlayer {
    actual fun play() {
        TODO("Not yet implemented for this platform")
    }

    actual fun playAsset(path: Path) {
        TODO("Not yet implemented for this platform")
    }

    actual fun pause() {
        TODO("Not yet implemented for this platform")
    }

    actual fun addItemsToQueue(paths: List<Path>) {
        TODO("Not yet implemented for this platform")
    }

    actual fun skipToNext() {
        TODO("Not yet implemented for this platform")
    }

    actual fun skipToPrevious() {
        TODO("Not yet implemented for this platform")
    }

    actual fun isPlaying(): Boolean {
        TODO("Not yet implemented for this platform")
    }
}