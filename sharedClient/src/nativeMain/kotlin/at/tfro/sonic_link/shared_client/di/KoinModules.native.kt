package at.tfro.sonic_link.shared_client.di

import at.tfro.sonic_link.shared_client.core.data.database.DatabaseFactory
import at.tfro.sonic_link.core.logger.PlatformLogger
import at.tfro.sonic_link.core.logger.PlatformLoggerFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single { DatabaseFactory() }
        single<PlatformLogger> { PlatformLoggerFactory().create() }
    }