package at.tfro.sonic_link.shared_client.library.presentation

import androidx.lifecycle.ViewModel
import at.tfro.sonic_link.core.logger.Log
import at.tfro.sonic_link.core.logger.i
import at.tfro.sonic_link.core.logger.tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibraryViewModel() : ViewModel() {
    companion object {
        const val TAG = "LibraryViewModel"
    }

    private val _state: MutableStateFlow<LibraryState> = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()


    fun onAction(action: LibraryAction) {
        when (action) {
            is LibraryAction.OnMusicClick -> {
                Log.tag(TAG).i { "Music clicked: ${action.music}" }
            }

            LibraryAction.OnBack -> {

            }
        }
    }
}