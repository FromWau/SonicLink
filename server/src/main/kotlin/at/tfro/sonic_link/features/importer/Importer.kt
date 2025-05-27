package at.tfro.sonic_link.features.importer

import at.tfro.sonic_link.Log
import at.tfro.sonic_link.ServerSettings
import kotlinx.serialization.Serializable
import java.io.File

class Importer {
    fun importAble(): Sequence<TriageMedia> {
        val folder = ServerSettings.triageFolder

        return folder.walk().filter { it.isFile }.map { file ->
                val parentPaths = file.parentFile.relativeTo(folder).path.split("/")

                val possibleArtist: String = parentPaths.getOrElse(0) { "" }
                val possibleAlbum: String = parentPaths.getOrElse(1) { "" }
                val possibleTitle: String = parentPaths.getOrElse(2) { file.nameWithoutExtension }


                val media = TriageMedia(
                    path = file.relativeTo(folder).path,
                    possibleTitle = normalizeNaming(possibleTitle),
                    possibleAlbum = normalizeNaming(possibleAlbum),
                    possibleArtist = normalizeNaming(possibleArtist),
                )
                return@map media
            }
    }

    fun import(media: TriageMedia) {
        val folder = ServerSettings.triageFolder
        val file = File(folder, media.path)
        if (!file.exists()) {
            throw IllegalArgumentException("File does not exist: $file")
        }

        Log.i { "Request to import file: $file" }
    }
}

@Serializable
data class TriageMedia(
    val path: String,
    val possibleTitle: String,
    val possibleAlbum: String,
    val possibleArtist: String,
)

fun normalizeNaming(raw: String): String {
    var title = raw

    // Snake case to spaced
    title = title.split('_').joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }

    // CamelCase to spaced
    title = title.replace(Regex("([a-z])([A-Z])"), "$1 $2").replaceFirstChar { it.uppercase() }

    return title
}
