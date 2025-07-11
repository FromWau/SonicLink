package at.tfro.sonic_link.shared_client.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import at.tfro.sonic_link.shared_client.core.data.database.DatabaseFactory
import at.tfro.sonic_link.shared_client.core.data.database.SettingDatabase
import at.tfro.sonic_link.shared_client.core.data.repository.SettingRepositoryImpl
import at.tfro.sonic_link.shared_client.core.domain.repository.SettingRepository
import at.tfro.sonic_link.shared_client.home.presentation.HomeViewModel
import at.tfro.sonic_link.shared_client.importer.data.data_source.ImporterDataSource
import at.tfro.sonic_link.shared_client.importer.data.data_source.ImporterRemoteDataSourceImpl
import at.tfro.sonic_link.shared_client.importer.data.network.ImporterApiClient
import at.tfro.sonic_link.shared_client.importer.data.repository.ImporterRepositoryImpl
import at.tfro.sonic_link.shared_client.importer.domain.repository.ImporterRepository
import at.tfro.sonic_link.shared_client.importer.presentation.import_media.ImportMediaViewModel
import at.tfro.sonic_link.shared_client.importer.presentation.importer_list.ImportListViewModel
import at.tfro.sonic_link.shared_client.library.presentation.LibraryViewModel
import at.tfro.sonic_link.core.logger.DebugLogger
import at.tfro.sonic_link.core.logger.Logger
import at.tfro.sonic_link.core.network.HttpClientFactory
import at.tfro.sonic_link.shared_client.settings.presentation.SettingsViewModel
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModules = module {
    singleOf(::ImporterApiClient)
    singleOf(::ImporterRemoteDataSourceImpl).bind<ImporterDataSource>()
    singleOf(::ImporterRepositoryImpl).bind<ImporterRepository>()

    singleOf(::SettingRepositoryImpl).bind<SettingRepository>()

    single {
        get<DatabaseFactory>().create()
            .fallbackToDestructiveMigrationOnDowngrade(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
    single { get<SettingDatabase>().settingDao }
}


val coreModule = module {
    singleOf(::DebugLogger).bind<Logger>()
    single<HttpClient> { HttpClientFactory.create(get(), get()) }
}

val viewModelModules = module {
    viewModelOf(::ImportMediaViewModel)
    viewModelOf(::ImportListViewModel)
    viewModelOf(::LibraryViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SettingsViewModel)
}
