package at.tfro.sonic_link.di

import at.tfro.sonic_link.logger.DebugLogger
import at.tfro.sonic_link.logger.Logger
import at.tfro.sonic_link.network.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    singleOf(::DebugLogger).bind<Logger>()
    single { HttpClientFactory.create(get(), get()) }
}