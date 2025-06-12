package at.tfro.sonic_link.di

import at.tfro.sonic_link.core.data.HttpClientFactory
import at.tfro.sonic_link.core.data.data_source.SettingsDataSource
import at.tfro.sonic_link.core.data.data_source.SettingsDataSourceImpl
import at.tfro.sonic_link.core.data.repository.SettingsRepositoryImpl
import at.tfro.sonic_link.core.domain.repository.SettingsRepository
import at.tfro.sonic_link.home.presentation.HomeViewModel
import at.tfro.sonic_link.importer.data.data_source.ImporterDataSource
import at.tfro.sonic_link.importer.data.data_source.ImporterRemoteDataSourceImpl
import at.tfro.sonic_link.importer.data.network.ImporterApiClient
import at.tfro.sonic_link.importer.data.repository.ImporterRepositoryImpl
import at.tfro.sonic_link.importer.domain.repository.ImporterRepository
import at.tfro.sonic_link.importer.presentation.ImporterViewModel
import at.tfro.sonic_link.library.presentation.LibraryViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModules, platformModule)
    }
}

val sharedModules = module {
    viewModelOf(::ImporterViewModel)
    viewModelOf(::LibraryViewModel)
    viewModelOf(::HomeViewModel)

    single { HttpClientFactory.create(get()) }
    singleOf(::ImporterApiClient)
    singleOf(::ImporterRemoteDataSourceImpl).bind<ImporterDataSource>()
    singleOf(::ImporterRepositoryImpl).bind<ImporterRepository>()

    singleOf(::SettingsRepositoryImpl).bind<SettingsRepository>()
    singleOf(::SettingsDataSourceImpl).bind<SettingsDataSource>()
}