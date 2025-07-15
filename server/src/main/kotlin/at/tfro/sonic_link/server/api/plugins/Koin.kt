package at.tfro.sonic_link.server.api.plugins

import at.tfro.sonic_link.core.logger.DebugLogger
import at.tfro.sonic_link.core.logger.Logger
import at.tfro.sonic_link.core.logger.PlatformLogger
import at.tfro.sonic_link.core.logger.PlatformLoggerFactory
import at.tfro.sonic_link.core.logger.d
import at.tfro.sonic_link.core.logger.e
import at.tfro.sonic_link.core.logger.i
import at.tfro.sonic_link.core.logger.tag
import at.tfro.sonic_link.core.logger.w
import at.tfro.sonic_link.core.musicbrainz_api.MusicbrainzApi
import at.tfro.sonic_link.core.network.HttpClientFactory
import at.tfro.sonic_link.server.ServerSettings
import at.tfro.sonic_link.server.data.MediaRepository
import at.tfro.sonic_link.server.data.MediaRepositoryImpl
import at.tfro.sonic_link.server.db.AppDatabase
import at.tfro.sonic_link.server.db.dao.AlbumDao
import at.tfro.sonic_link.server.db.dao.ArtistDao
import at.tfro.sonic_link.server.db.dao.MediaDao
import at.tfro.sonic_link.server.db.getAppDatabase
import at.tfro.sonic_link.server.features.importer.Importer
import at.tfro.sonic_link.server.sync.data.SyncRepositoryImpl
import at.tfro.sonic_link.server.sync.data.database.SyncDao
import at.tfro.sonic_link.server.sync.data.database.SyncDatabase
import at.tfro.sonic_link.server.sync.data.database.getSyncDatabase
import at.tfro.sonic_link.server.sync.domain.SyncServiceImpl
import at.tfro.sonic_link.server.sync.domain.repository.SyncRepository
import at.tfro.sonic_link.shared_rpc.sync.SyncService
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.logger.Level
import org.koin.core.logger.MESSAGE
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.core.logger.Logger as KoinLogger

val coreModule = module {
    singleOf(::ServerSettings)
    single<PlatformLogger> {
        PlatformLoggerFactory(
            get<ServerSettings>().dataDir
        ).create()
    }
    singleOf(::DebugLogger) bind Logger::class
    single<HttpClient> { HttpClientFactory.create(get(), get()) }
}

val serverModule = module {
    single { getAppDatabase() }
    single<MediaDao> { get<AppDatabase>().mediaDao() }
    single<AlbumDao> { get<AppDatabase>().albumDao() }
    single<ArtistDao> { get<AppDatabase>().artistDao() }

    singleOf(::MediaRepositoryImpl) bind MediaRepository::class

    single { getSyncDatabase() }
    single<SyncDao> { get<SyncDatabase>().syncDao() }

    singleOf(::SyncRepositoryImpl) bind SyncRepository::class
    factoryOf(::SyncServiceImpl) bind SyncService::class

    singleOf(::MusicbrainzApi)
    singleOf(::Importer)
}

fun Application.configureKoin() {
    install(Koin) {
        logger(KoinLoggerAdapter(Level.DEBUG))
        modules(coreModule, serverModule)
    }
}

class KoinLoggerAdapter(level: Level = Level.INFO) : KoinLogger(level) {
    // Create a logger instance without DI
    val dataDir = ServerSettings().dataDir
    val loggerFactory = PlatformLoggerFactory(dataDir)

    val logger = DebugLogger(loggerFactory.create()).tag("Koin")

    override fun display(level: Level, msg: MESSAGE) {
        when (level) {
            Level.DEBUG -> logger.d { msg }
            Level.INFO -> logger.i { msg }
            Level.ERROR -> logger.e { msg }
            Level.WARNING -> logger.w { msg }
            else -> logger.e { msg }
        }
    }
}
