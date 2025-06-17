package at.tfro.sonic_link.importer.presentation.importer_list

sealed interface ImportListAction {
    data object OnBack : ImportListAction
}