package at.tfro.sonic_link.shared_client.di

import at.tfro.sonic_link.core.di.coreModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(coreModule, sharedModules, viewModelModules)
    }
}
