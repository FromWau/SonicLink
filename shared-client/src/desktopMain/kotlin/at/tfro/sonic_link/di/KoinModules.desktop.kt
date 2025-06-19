package at.tfro.sonic_link.di

import at.tfro.sonic_link.core.data.database.DatabaseFactory
import at.tfro.sonic_link.logger.PlatformLogger
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { DatabaseFactory() }
        single { PlatformLogger() }
    }