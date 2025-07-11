package at.tfro.sonic_link.shared_client.importer.presentation.importer_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.tfro.sonic_link.shared_client.importer.domain.repository.ImporterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImportListViewModel(
    private val importerRepository: ImporterRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ImportListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            importerRepository.getAllImportableMedia().let { media ->
                _state.update { current ->
                    current.copy(mediaToImport = media, isLoading = false)
                }
            }
        }
    }

    fun onAction(action: ImportListAction) {
        when (action) {
            ImportListAction.OnBack -> {
                _state.update {
                    if (it.mediaToIdentify != null) {
                        it.copy(mediaToIdentify = null)
                    } else {
                        it
                    }
                }
            }
        }
    }
}
