package at.tfro.sonic_link.db.tables

import org.jetbrains.exposed.sql.Table

object Artist : Table("artists") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val path = varchar("path", 255).uniqueIndex()
    val art = varchar("artist_art_url", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}