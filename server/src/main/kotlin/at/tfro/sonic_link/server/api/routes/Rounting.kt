@file:OptIn(ExperimentalTime::class)

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
import at.tfro.sonic_link.server.sync.domain.model.SyncVersion
import at.tfro.sonic_link.server.sync.domain.repository.SyncRepository
import at.tfro.sonic_link.server.sync.routing.syncRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.ktor.ext.inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
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

                val syncVersion = SyncVersion(
                    version = 1L,
                    releasedAt = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                )

                val artistNirvana = Artist(
                    id = Uuid.random(),
                    name = "Nirvana",
                    coverArtPath = null,
                    path = "Nirvana",
                    syncVersion = syncVersion,
                    lastModified = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                    isDeleted = false
                )

                val albumBleach = Album(
                    id = Uuid.random(),
                    title = "Bleach",
                    artist = artistNirvana,
                    coverArtPath = null,
                    path = "Nirvana/Bleach",
                    syncVersion = syncVersion,
                    lastModified = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                    isDeleted = false
                )

                val recordSmellsLikeTeenSpirit = Record(
                    id = Uuid.random(),
                    title = "Smells Like Teen Spirit",
                    album = albumBleach,
                    artist = artistNirvana,
                    path = "Nirvana/Bleach/SmellsLikeTeenSpirit.mp3",
                    syncVersion = syncVersion,
                    lastModified = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                    isDeleted = false
                )
                syncRepo.insertRecord(recordSmellsLikeTeenSpirit)

                val recordComeAsYouAre = Record(
                    id = Uuid.random(),
                    title = "Come As You Are",
                    album = albumBleach,
                    artist = artistNirvana,
                    path = "Nirvana/Bleach/ComeAsYouAre.mp3",
                    syncVersion = syncVersion,
                    lastModified = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                    isDeleted = false
                )
                syncRepo.insertRecord(recordComeAsYouAre)

                val recordLithium = Record(
                    id = Uuid.random(),
                    title = "Lithium",
                    album = albumBleach,
                    artist = artistNirvana,
                    path = "Nirvana/Bleach/Lithium.mp3",
                    syncVersion = syncVersion,
                    lastModified = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                    isDeleted = false
                )
                syncRepo.insertRecord(recordLithium)

                val recordInBloom = Record(
                    id = Uuid.random(),
                    title = "In Bloom",
                    album = albumBleach,
                    artist = artistNirvana,
                    path = "Nirvana/Bleach/InBloom.mp3",
                    syncVersion = syncVersion,
                    lastModified = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                    isDeleted = false
                )
                syncRepo.insertRecord(recordInBloom)

                val recordPolly = Record(
                    id = Uuid.random(),
                    title = "Polly",
                    album = albumBleach,
                    artist = artistNirvana,
                    path = "Nirvana/Bleach/Polly.mp3",
                    syncVersion = syncVersion,
                    lastModified = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                    isDeleted = false
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

        get("/sync/versions") {
            try {
                val syncRepo by inject<SyncRepository>()
                val versions = syncRepo.getAllSyncVersions()

                if (versions.isEmpty()) {
                    call.respond(HttpStatusCode.OK, "No sync versions found.")
                    return@get
                }

                call.respond(
                    HttpStatusCode.OK,
                    versions.joinToString(separator = "\n\n") { it.toPrettyString() }
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    "Failed to fetch sync versions: ${e.message}"
                )
            }
        }

        get("/sync/current") {
            try {
                val syncRepo by inject<SyncRepository>()
                val currentSyncVersion = syncRepo.getCurrentSyncVersionFlow().firstOrNull()

                if (currentSyncVersion == null) {
                    call.respond(HttpStatusCode.NotFound, "No current sync version found.")
                    return@get
                }

                call.respond(HttpStatusCode.OK, currentSyncVersion.toPrettyString())
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    "Failed to fetch current sync version: ${e.message}"
                )
            }
        }

        get("/delta/{sourceVersion}/{targetVersion}") {
            val sourceVersionParam = call.parameters["sourceVersion"]
            val targetVersionParam = call.parameters["targetVersion"]

            if (sourceVersionParam == null || targetVersionParam == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Both source and target versions must be provided."
                )
                return@get
            }

            try {
                val syncRepo by inject<SyncRepository>()
                val sourceVersion = SyncVersion(
                    version = sourceVersionParam.toLong(),
                    releasedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )
                val targetVersion = SyncVersion(
                    version = targetVersionParam.toLong(),
                    releasedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )

                val sourceSyncVersion = syncRepo.findByVersion(sourceVersion.version)
                val targetSyncVersion = syncRepo.findByVersion(targetVersion.version)
                if (sourceSyncVersion == null || targetSyncVersion == null) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        "One or both sync versions not found."
                    )
                    return@get
                }


                val delta = syncRepo.generateDelta(sourceVersion, targetVersion)
                call.respond(HttpStatusCode.OK, delta.toPrettyString())
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    "Failed to generate delta: ${e.message}"
                )
            }
        }

        get("/yeet") {
            try {
                val syncRepo by inject<SyncRepository>()
                syncRepo.clearAllData()

                call.respond(HttpStatusCode.OK, "All data cleared successfully.")
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    "Failed to clear data: ${e.message}"
                )
            }
        }
    }
}