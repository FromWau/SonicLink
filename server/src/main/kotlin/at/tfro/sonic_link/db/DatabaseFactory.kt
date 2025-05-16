package at.tfro.sonic_link.db

import at.tfro.sonic_link.db.tables.Album
import at.tfro.sonic_link.db.tables.AlbumArtist
import at.tfro.sonic_link.db.tables.Artist
import at.tfro.sonic_link.db.tables.Media
import at.tfro.sonic_link.db.tables.MediaArtist
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun initH2() {
        Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
            driver = "org.h2.Driver",
            user = "root",
            password = ""
        )

        createTables()
    }

    fun init(
        url: String,
        driver: String,
        user: String,
        password: String,
    ) {
        Database.connect(url = url, driver = driver, user = user, password = password)

        createTables()
    }
}

private fun createTables() = transaction {
    SchemaUtils.create(
        Artist,
        Album,
        AlbumArtist,
        Media,
        MediaArtist
    )
}