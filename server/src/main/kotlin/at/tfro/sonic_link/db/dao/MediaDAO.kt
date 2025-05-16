package at.tfro.sonic_link.db.dao

import at.tfro.sonic_link.db.tables.Artist
import at.tfro.sonic_link.db.tables.Media
import at.tfro.sonic_link.db.tables.MediaArtist
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

data class MediaDTO(
    val id: Int,
    val name: String,
    val path: String,
    val mediaType: MediaType,
    val album: AlbumDTO?,
    val artists: List<ArtistDTO>,
) {
    enum class MediaType {
        AUDIO,
        VIDEO;

        fun toDb(): String = this.name.lowercase()

        companion object {
            fun fromDb(dbValue: String): MediaType = MediaType.entries.firstOrNull {
                it.name.lowercase() == dbValue
            } ?: throw IllegalArgumentException("Unknown media type: $dbValue")
        }
    }
}

object MediaDAO {
    fun create(
        name: String,
        path: String,
        mediaType: MediaDTO.MediaType,
        albumId: Int?,
        artistIds: List<Int>,
    ): MediaDTO = transaction {
        val mediaId = Media.insert {
            it[Media.name] = name
            it[Media.path] = path
            it[Media.mediaType] = mediaType.toDb()
            it[Media.album] = albumId
        }[Media.id]

        artistIds.forEach { artistId ->
            MediaArtist.insert {
                it[media] = mediaId
                it[artist] = artistId
            }
        }

        findById(mediaId)!!
    }

    fun findById(id: Int): MediaDTO? = transaction {
        val mediaRow = Media.selectAll().singleOrNull() ?: return@transaction null

        val albumDto = mediaRow[Media.album]?.let { albumId ->
            AlbumDAO.findById(albumId)
        }

        val artistDtos = (MediaArtist innerJoin Artist)
            .selectAll()
            .where { MediaArtist.media eq id }
            .map { ArtistDAO.toDTO(it) }

        MediaDTO(
            id = mediaRow[Media.id],
            name = mediaRow[Media.name],
            path = mediaRow[Media.path],
            mediaType = MediaDTO.MediaType.fromDb(mediaRow[Media.mediaType]),
            album = albumDto,
            artists = artistDtos
        )
    }
}
