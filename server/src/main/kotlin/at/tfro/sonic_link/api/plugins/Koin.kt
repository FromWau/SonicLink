package at.tfro.sonic_link.api.plugins

import at.tfro.sonic_link.data.MediaRepository
import at.tfro.sonic_link.data.MediaRepositoryImpl
import at.tfro.sonic_link.db.AppDatabase
import at.tfro.sonic_link.db.dao.MediaDao
import at.tfro.sonic_link.db.getDatabase
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val daoModule = module {
    single { getDatabase() }
    single<MediaDao> { get<AppDatabase>().mediaDao() }
}

val repositoryModule = module {
    single<MediaRepository> { MediaRepositoryImpl(get()) }
}

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger(level = Level.DEBUG)
        modules(daoModule, repositoryModule)
    }
}