package at.tfro.sonic_link.db.tables

import org.jetbrains.exposed.sql.Table

object Media : Table("media") {
    val id = integer("id").autoIncrement() // Primary key
    val name = varchar("name", 255)
    val path = varchar("path", 255).uniqueIndex()
    val mediaType = varchar("media_type", 20) // store MediaType as string
    val album = reference("album_id", Album.id).nullable()

    override val primaryKey = PrimaryKey(id)
}
