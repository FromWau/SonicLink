package at.tfro.sonic_link.importer.presentation.importer

import at.tfro.sonic_link.importer.domain.ImportMedia

sealed interface ImporterAction {
    data class OnMediaSelected(val media: ImportMedia) : ImporterAction
    data class OnMediaUnselected(val media: ImportMedia) : ImporterAction
    data class OnSearchQueryChanged(val query: String) : ImporterAction
    data class OnTabSelected(val index: Int) : ImporterAction
    data class OnImportMedia(val media: List<ImportMedia>) : ImporterAction
    data class OnError(val error: String) : ImporterAction
    object OnBack : ImporterAction
}