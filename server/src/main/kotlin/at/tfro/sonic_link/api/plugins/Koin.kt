package at.tfro.sonic_link.api.plugins

import at.tfro.sonic_link.ServerSettings
import at.tfro.sonic_link.data.MediaRepository
import at.tfro.sonic_link.data.MediaRepositoryImpl
import at.tfro.sonic_link.db.AppDatabase
import at.tfro.sonic_link.db.dao.AlbumDao
import at.tfro.sonic_link.db.dao.ArtistDao
import at.tfro.sonic_link.db.dao.MediaDao
import at.tfro.sonic_link.db.getDatabase
import at.tfro.sonic_link.features.importer.Importer
import at.tfro.sonic_link.logger.DebugLogger
import at.tfro.sonic_link.logger.Logger
import at.tfro.sonic_link.logger.PlatformLogger
import at.tfro.sonic_link.logger.PlatformLoggerFactory
import at.tfro.sonic_link.logger.d
import at.tfro.sonic_link.logger.e
import at.tfro.sonic_link.logger.i
import at.tfro.sonic_link.logger.tag
import at.tfro.sonic_link.logger.w
import at.tfro.sonic_link.musicbrainz_api.MusicbrainzApi
import at.tfro.sonic_link.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.logger.Level
import org.koin.core.logger.MESSAGE
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
    singleOf(::DebugLogger).bind<Logger>()
    single<HttpClient> { HttpClientFactory.create(get(), get()) }
}

val serverModule = module {
    single { getDatabase() }
    single<MediaDao> { get<AppDatabase>().mediaDao() }
    single<AlbumDao> { get<AppDatabase>().albumDao() }
    single<ArtistDao> { get<AppDatabase>().artistDao() }

    single<MediaRepository> { MediaRepositoryImpl(get()) }

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
