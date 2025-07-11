package at.tfro.sonic_link.shared_client.importer.presentation.import_media

sealed interface ImportMediaAction {
    data object OnBack : ImportMediaAction
}