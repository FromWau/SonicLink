package at.tfro.sonic_link.server.api.routes

import at.tfro.sonic_link.core.logger.Logger
import at.tfro.sonic_link.core.logger.e
import at.tfro.sonic_link.core.logger.i
import at.tfro.sonic_link.core.logger.tag
import at.tfro.sonic_link.core.musicbrainz_api.MusicbrainzApi
import at.tfro.sonic_link.server.api.routes.triage.triageRoutes
import at.tfro.sonic_link.server.sync.domain.model.Album
import at.tfro.sonic_link.server.sync.domain.model.Artist
import at.tfro.sonic_link.server.sync.domain.model.Record
import at.tfro.sonic_link.server.sync.domain.repository.SyncRepository
import at.tfro.sonic_link.server.sync.routing.syncRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import kotlin.uuid.Uuid

fun Application.configureRouting() {
    val musicbrainzApi by inject<MusicbrainzApi>()

    routing {
        val logger by inject<Logger>()

        get("/") {
            call.respond(HttpStatusCode.OK, "OK")
        }

        triageRoutes()
        streamRoutes()
        syncRoutes()

        get("/search") {
            val artist = call.request.queryParameters["artist"]
            val album = call.request.queryParameters["album"]
            val recording = call.request.queryParameters["recording"]

            if (artist.isNullOrEmpty() && album.isNullOrEmpty() && recording.isNullOrEmpty()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "At least one search parameter must be provided."
                )
                return@get
            }

            val search = musicbrainzApi.search(
                artist = artist,
                album = album,
                recording = recording
            )
            call.respond(HttpStatusCode.OK, search)
        }

        get("/initData") {
            try {
                val syncRepo by inject<SyncRepository>()

                val artistNirvana = Artist(
                    id = Uuid.random(),
                    name = "Nirvana",
                    coverArtPath = null,
                    path = "Nirvana",
                )

                val albumBleach = Album(
                    id = Uuid.random(),
                    title = "Bleach",
                    artist = artistNirvana,
                    coverArtPath = null,
                    path = "Nirvana/Bleach",
                )

                val recordSmellsLikeTeenSpirit = Record(
                    id = Uuid.random(),
                    title = "Smells Like Teen Spirit",
                    album = albumBleach,
                    artist = artistNirvana,
                    path = "Nirvana/Bleach/SmellsLikeTeenSpirit.mp3"
                )
                syncRepo.insertRecord(recordSmellsLikeTeenSpirit)

                val recordComeAsYouAre = Record(
                    id = Uuid.random(),
                    title = "Come As You Are",
                    album = albumBleach,
                    artist = artistNirvana,
                    path = "Nirvana/Bleach/ComeAsYouAre.mp3"
                )
                syncRepo.insertRecord(recordComeAsYouAre)

                val recordLithium = Record(
                    id = Uuid.random(),
                    title = "Lithium",
                    album = albumBleach,
                    artist = artistNirvana,
                    path = "Nirvana/Bleach/Lithium.mp3"
                )
                syncRepo.insertRecord(recordLithium)

                val recordInBloom = Record(
                    id = Uuid.random(),
                    title = "In Bloom",
                    album = albumBleach,
                    artist = artistNirvana,
                    path = "Nirvana/Bleach/InBloom.mp3"
                )
                syncRepo.insertRecord(recordInBloom)

                val recordPolly = Record(
                    id = Uuid.random(),
                    title = "Polly",
                    album = albumBleach,
                    artist = artistNirvana,
                    path = "Nirvana/Bleach/Polly.mp3"
                )
                syncRepo.insertRecord(recordPolly)



                logger.tag("initData").i { "initial data setup successful" }
                call.respond(HttpStatusCode.OK, "Initial data setup successful")

            } catch (e: Exception) {
                logger.tag("initData").e { "Failed to set up initial data: ${e.message}" }
                call.respond(
                    HttpStatusCode.InternalServerError,
                    "Failed to set up initial data: ${e.message}"
                )
            }
        }

        get("/artists") {
            try {
                val syncRepo by inject<SyncRepository>()
                val artists = syncRepo.getAllArtists()

                if (artists.isEmpty()) {
                    logger.tag("artists").i { "No artists found." }
                    call.respond(HttpStatusCode.OK, "No artists found.")
                    return@get
                }

                logger.tag("artists").i { "Fetched ${artists.size} artists." }
                call.respond(
                    HttpStatusCode.OK,
                    artists.joinToString(separator = "\n\n") { it.toPrettyString() }
                )

            } catch (e: Exception) {
                logger.tag("artists").e { "Failed to fetch artists: ${e.message}" }
                call.respond(
                    HttpStatusCode.InternalServerError,
                    "Failed to fetch artists: ${e.message}"
                )
            }
        }

        get("/albums") {
            try {
                val syncRepo by inject<SyncRepository>()
                val albums = syncRepo.getAllAlbums()

                if (albums.isEmpty()) {
                    logger.tag("albums").i { "No albums found." }
                    call.respond(HttpStatusCode.OK, "No albums found.")
                    return@get
                }

                logger.tag("albums").i { "Fetched ${albums.size} albums." }
                call.respond(
                    HttpStatusCode.OK,
                    albums.joinToString(separator = "\n\n") { it.toPrettyString() }
                )

            } catch (e: Exception) {
                logger.tag("albums").e { "Failed to fetch albums: ${e.message}" }
                call.respond(
                    HttpStatusCode.InternalServerError,
                    "Failed to fetch albums: ${e.message}"
                )
            }
        }

        get("/records") {
            try {
                val syncRepo by inject<SyncRepository>()
                val records = syncRepo.getAllRecords()

                if (records.isEmpty()) {
                    logger.tag("records").i { "No records found." }
                    call.respond(HttpStatusCode.OK, "No records found.")
                    return@get
                }

                logger.tag("records").i { "Fetched ${records.size} records." }
                call.respond(
                    HttpStatusCode.OK,
                    records.joinToString(separator = "\n\n") { it.toPrettyString() }
                )

            } catch (e: Exception) {
                logger.tag("records").e { "Failed to fetch records: ${e.message}" }
                call.respond(
                    HttpStatusCode.InternalServerError,
                    "Failed to fetch records: ${e.message}"
                )
            }
        }

        get("/yeet") {
            try {
                val syncRepo by inject<SyncRepository>()
                syncRepo.clearAllData()

                call.respond(HttpStatusCode.OK, "All data cleared successfully.")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Failed to clear data: ${e.message}")
            }
        }
    }
}