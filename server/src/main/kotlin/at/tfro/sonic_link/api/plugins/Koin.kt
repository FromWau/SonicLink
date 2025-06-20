package at.tfro.sonic_link.api.plugins

import at.tfro.sonic_link.ServerSettings
import at.tfro.sonic_link.data.MediaRepository
import at.tfro.sonic_link.data.MediaRepositoryImpl
import at.tfro.sonic_link.db.AppDatabase
import at.tfro.sonic_link.db.dao.MediaDao
import at.tfro.sonic_link.db.getDatabase
import at.tfro.sonic_link.features.importer.Importer
import at.tfro.sonic_link.logger.DebugLogger
import at.tfro.sonic_link.logger.Logger
import at.tfro.sonic_link.logger.PlatformLogger
import at.tfro.sonic_link.musicbrainz_api.MusicbrainzApi
import at.tfro.sonic_link.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.logger.Level
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val coreModule = module {
    single { PlatformLogger() }
    singleOf(::DebugLogger).bind<Logger>()
    single<HttpClient> { HttpClientFactory.create(get(), get()) }
}

val serverModule = module {
    single { getDatabase() }
    single<MediaDao> { get<AppDatabase>().mediaDao() }
    single<MediaRepository> { MediaRepositoryImpl(get()) }

    singleOf(::ServerSettings)
    singleOf(::MusicbrainzApi)
    singleOf(::Importer)
}

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger(level = Level.DEBUG)
        modules(coreModule, serverModule)
    }
}