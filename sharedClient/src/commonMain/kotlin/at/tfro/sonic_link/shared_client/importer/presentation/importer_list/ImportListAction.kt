package at.tfro.sonic_link.shared_client.importer.presentation.importer_list

sealed interface ImportListAction {
    data object OnBack : ImportListAction
}