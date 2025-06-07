package at.tfro.sonic_link.importer.presentation.importer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ImporterViewmodel() : ViewModel() {
    private val _state = MutableStateFlow(ImporterState())
    val state = _state.asStateFlow()

    fun onAction(action: ImporterAction) {
        when (action) {
            ImporterAction.OnBack -> {}
            is ImporterAction.OnError -> {}
            is ImporterAction.OnImportMedia -> {}
            is ImporterAction.OnMediaSelected -> {}
            is ImporterAction.OnMediaUnselected -> {}
            is ImporterAction.OnSearchQueryChanged -> {}
            is ImporterAction.OnTabSelected -> {}
        }
    }
}