package at.tfro.sonic_link.importer.presentation.import_media

import androidx.lifecycle.ViewModel
import at.tfro.sonic_link.importer.domain.repository.ImporterRepository
import at.tfro.sonic_link.importer.presentation.importer_list.ImportListAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ImportMediaViewModel(
    private val importerRepository: ImporterRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ImportMediaState())
    val state = _state.asStateFlow()

    fun onAction(action: ImportListAction) {
        when (action) {
            ImportListAction.OnBack -> {

            }
        }
    }
}
