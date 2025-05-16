package at.tfro.sonic_link.db.tables

import org.jetbrains.exposed.sql.Table

object AlbumArtist : Table("album_artist") {
    val album = reference("album_id", Album.id)
    val artist = reference("artist_id", Artist.id)

    override val primaryKey = PrimaryKey(album, artist)
}
