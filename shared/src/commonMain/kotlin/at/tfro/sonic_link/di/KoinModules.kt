package at.tfro.sonic_link.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import at.tfro.sonic_link.core.data.network.HttpClientFactory
import at.tfro.sonic_link.core.data.database.DatabaseFactory
import at.tfro.sonic_link.core.data.database.SettingDatabase
import at.tfro.sonic_link.core.data.repository.SettingRepositoryImpl
import at.tfro.sonic_link.core.domain.repository.SettingRepository
import at.tfro.sonic_link.home.presentation.HomeViewModel
import at.tfro.sonic_link.importer.data.data_source.ImporterDataSource
import at.tfro.sonic_link.importer.data.data_source.ImporterRemoteDataSourceImpl
import at.tfro.sonic_link.importer.data.network.ImporterApiClient
import at.tfro.sonic_link.importer.data.repository.ImporterRepositoryImpl
import at.tfro.sonic_link.importer.domain.repository.ImporterRepository
import at.tfro.sonic_link.importer.presentation.ImporterViewModel
import at.tfro.sonic_link.library.presentation.LibraryViewModel
import at.tfro.sonic_link.settings.presentation.SettingsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModules = module {
    viewModelOf(::ImporterViewModel)
    viewModelOf(::LibraryViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SettingsViewModel)

    single { HttpClientFactory.create(get()) }
    singleOf(::ImporterApiClient)
    singleOf(::ImporterRemoteDataSourceImpl).bind<ImporterDataSource>()
    singleOf(::ImporterRepositoryImpl).bind<ImporterRepository>()

    singleOf(::SettingRepositoryImpl).bind<SettingRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<SettingDatabase>().settingDao }
}