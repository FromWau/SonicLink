package at.tfro.sonic_link.db.dao

import at.tfro.sonic_link.db.tables.Album
import at.tfro.sonic_link.db.tables.AlbumArtist
import at.tfro.sonic_link.db.tables.Artist
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

data class AlbumDTO(
    val id: Int,
    val name: String,
    val path: String,
    val art: String?,
    val artists: List<ArtistDTO>,
)

object AlbumDAO {
    fun create(name: String, path: String, art: String?, artistIds: List<Int>): AlbumDTO =
        transaction {

            val albumId = Album.insert {
                it[Album.name] = name
                it[Album.path] = path
                it[Album.art] = art
            }[Album.id]

            // Insert into join table
            artistIds.forEach { artistId ->
                AlbumArtist.insert {
                    it[album] = albumId
                    it[artist] = artistId
                }
            }

            findById(albumId)!!
        }

    fun findById(id: Int): AlbumDTO? = transaction {
        val albumRow = Album.selectAll()
            .where { Album.id.eq(id) }
            .singleOrNull() ?: return@transaction null

        val artistRows = (AlbumArtist innerJoin Artist)
            .selectAll()
            .where { AlbumArtist.album eq id }
            .map { ArtistDAO.toDTO(it) }

        AlbumDTO(
            id = albumRow[Album.id],
            name = albumRow[Album.name],
            path = albumRow[Album.path],
            art = albumRow[Album.art],
            artists = artistRows
        )
    }
}
