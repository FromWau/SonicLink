package at.tfro.sonic_link.importer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.tfro.sonic_link.importer.domain.model.ImportMedia
import at.tfro.sonic_link.importer.domain.repository.ImporterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImporterViewModel(
    private val importerRepository: ImporterRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ImporterState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            importerRepository.getAllImportableMedia()
                .map {
                    ImportMedia(
                        path = it,
                        title = "",
                        album = "",
                        artist = "",
                    )
                }
                .let { media ->
                    _state.update { current ->
                        current.copy(mediaToImport = media, isLoading = false)
                    }
                }
        }
    }

    fun onAction(action: ImporterAction) {
        when (action) {
            ImporterAction.OnBack -> {
                _state.update {
                    if (it.mediaToIdentify != null) {
                        it.copy(mediaToIdentify = null)
                    } else {
                        it
                    }
                }
            }

            is ImporterAction.OnError -> {}
            is ImporterAction.OnImportableMediaSelected -> {
                _state.update {
                    it.copy(mediaToIdentify = action.media)
                }
            }

            is ImporterAction.OnImportMedia -> {}
            is ImporterAction.OnMediaUnselected -> {}
            is ImporterAction.OnSearchQueryChanged -> {}
            is ImporterAction.OnTabSelected -> {}
        }
    }
}
