package at.tfro.sonic_link.features.importer

import at.tfro.sonic_link.Log
import at.tfro.sonic_link.ServerSettings
import kotlinx.serialization.Serializable
import java.io.File

class Importer {
    fun importAble(): Sequence<ImportMedia> {
        val folder = ServerSettings.triageFolder

        return folder.walk()
            .filter { it.isFile }
            .map { file ->
                val mediaType: ImportMedia.MediaType = ImportMedia.MediaType.fromFile(file)
                val parentPath = file.parentFile.relativeTo(folder).path

                val parentPaths = parentPath.split("/")
                val possibleArtist = parentPaths.getOrNull(0) ?: ""
                val possibleAlbum = parentPaths.getOrNull(1) ?: ""
                val possibleTitle = parentPaths.getOrNull(2) ?: file.name

                val media = ImportMedia(
                    path = parentPath,
                    type = mediaType,
                    title = possibleTitle,
                    artist = possibleArtist,
                    album = possibleAlbum,
                )
                return@map media
            }
    }

    fun import(media: ImportMedia) {
        val folder = ServerSettings.triageFolder
        val file = File(folder, media.path)
        if (!file.exists()) {
            throw IllegalArgumentException("File does not exist: $file")
        }

        Log.i { "Request to import file: $file" }
    }
}

@Serializable
data class ImportMedia(
    val path: String,
    val type: MediaType,
    val title: String,
    val album: String,
    val artist: String,
) {
    @Serializable
    enum class MediaType {
        AUDIO,
        VIDEO,
        UNKNOWN,
        ;

        companion object {
            fun fromFile(file: File): MediaType {
                return when (file.extension.lowercase()) {
                    "mp3", "flac", "wav" -> AUDIO
                    "mp4", "mkv", "avi" -> VIDEO
                    else -> UNKNOWN
                }
            }
        }
    }
}