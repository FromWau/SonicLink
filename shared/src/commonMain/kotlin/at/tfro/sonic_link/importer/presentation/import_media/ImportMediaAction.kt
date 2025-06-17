package at.tfro.sonic_link.importer.presentation.import_media

sealed interface ImportMediaAction {
    data object OnBack : ImportMediaAction
}