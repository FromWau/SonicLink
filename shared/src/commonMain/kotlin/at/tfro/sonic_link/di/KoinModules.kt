package at.tfro.sonic_link.di

import at.tfro.sonic_link.importer.presentation.ImporterViewModel
import at.tfro.sonic_link.library.presentation.MusicListViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
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
    viewModelOf(::MusicListViewModel)
}