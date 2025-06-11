package at.tfro.sonic_link.library.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibraryViewModel : ViewModel() {
    private val _state: MutableStateFlow<LibraryState> = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    fun onAction(action: LibraryAction) {
        when (action) {
            is LibraryAction.OnMusicClick -> {

            }

            LibraryAction.OnBack -> {

            }
        }
    }
}