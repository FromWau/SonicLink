package at.tfro.sonic_link.importer.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ImporterViewModel : ViewModel() {
    private val _state = MutableStateFlow(ImporterState())
    val state = _state.asStateFlow()

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
