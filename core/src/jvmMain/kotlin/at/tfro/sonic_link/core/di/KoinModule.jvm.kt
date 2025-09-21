package at.tfro.sonic_link.core.di

import at.tfro.sonic_link.core.SystemAppDirectories
import at.tfro.sonic_link.core.database.DatabaseFactory
import at.tfro.sonic_link.core.media.MediaPlayer
import at.tfro.sonic_link.core.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val coreModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single<HttpClient> { HttpClientFactory.create(get()) }
        single<SystemAppDirectories> { SystemAppDirectories() }
        single<DatabaseFactory> { DatabaseFactory(get()) }
        single<MediaPlayer> { MediaPlayer() }
    }
