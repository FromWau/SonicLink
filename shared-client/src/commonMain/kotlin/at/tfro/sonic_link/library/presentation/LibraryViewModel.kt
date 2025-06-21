package at.tfro.sonic_link.library.presentation

import androidx.lifecycle.ViewModel
import at.tfro.sonic_link.logger.Logger
import at.tfro.sonic_link.logger.i
import at.tfro.sonic_link.logger.tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibraryViewModel(
    private val logger: Logger,
) : ViewModel() {
    private val _state: MutableStateFlow<LibraryState> = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    companion object {
        const val LOG_TAG = "LibraryViewModel"
    }

    init {
        logger.tag(LOG_TAG).i { "LibraryViewModel initialized" }
    }

    fun onAction(action: LibraryAction) {
        when (action) {
            is LibraryAction.OnMusicClick -> {
                logger.tag(LOG_TAG).i { "Music clicked: ${action.music}" }
            }

            LibraryAction.OnBack -> {

            }
        }
    }
}