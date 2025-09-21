package at.tfro.sonic_link.server.api.plugins

import at.tfro.sonic_link.core.di.coreModule
import at.tfro.sonic_link.core.logger.Log
import at.tfro.sonic_link.core.musicbrainz_api.MusicbrainzApi
import at.tfro.sonic_link.server.features.importer.Importer
import at.tfro.sonic_link.server.sync.data.SyncRepositoryImpl
import at.tfro.sonic_link.server.sync.data.database.SyncDatabase
import at.tfro.sonic_link.server.sync.data.database.dao.AlbumDao
import at.tfro.sonic_link.server.sync.data.database.dao.ArtistDao
import at.tfro.sonic_link.server.sync.data.database.dao.RecordDao
import at.tfro.sonic_link.server.sync.data.database.dao.SyncVersionDao
import at.tfro.sonic_link.server.sync.data.database.getSyncDatabase
import at.tfro.sonic_link.server.sync.domain.SyncServiceImpl
import at.tfro.sonic_link.server.sync.domain.repository.SyncRepository
import at.tfro.sonic_link.shared_rpc.sync.SyncService
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

val serverModule = module {
    single { getSyncDatabase() }
    single<SyncVersionDao> { get<SyncDatabase>().syncVersionDao() }
    single<ArtistDao> { get<SyncDatabase>().artistDao() }
    single<AlbumDao> { get<SyncDatabase>().albumDao() }
    single<RecordDao> { get<SyncDatabase>().recordDao() }

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
    val logger = Log.tag("Koin")

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
