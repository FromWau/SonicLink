package at.tfro.sonic_link.db.dao

import at.tfro.sonic_link.db.tables.Artist
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

data class ArtistDTO(
    val id: Int,
    val name: String,
    val path: String,
    val art: String?,
)

object ArtistDAO {
    fun create(name: String, path: String, art: String?): ArtistDTO = transaction {
        val id = Artist.insert {
            it[Artist.name] = name
            it[Artist.path] = path
            it[Artist.art] = art
        }[Artist.id]

        ArtistDTO(id, name, path, art)
    }

    fun findById(id: Int): ArtistDTO? = transaction {
        Artist.selectAll()
            .where { Artist.id eq id }
            .mapNotNull { toDTO(it) }
            .singleOrNull()
    }

    fun toDTO(row: ResultRow) = ArtistDTO(
        id = row[Artist.id],
        name = row[Artist.name],
        path = row[Artist.path],
        art = row[Artist.art]
    )
}
