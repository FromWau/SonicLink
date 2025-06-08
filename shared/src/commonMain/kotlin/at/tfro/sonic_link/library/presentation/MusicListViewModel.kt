package at.tfro.sonic_link.library.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicListViewModel : ViewModel() {
    private val _state: MutableStateFlow<MusicListState> = MutableStateFlow(MusicListState())
    val state = _state.asStateFlow()

    fun onAction(action: MusicListAction) {
        when (action) {
            is MusicListAction.OnMusicClick -> {

            }

            MusicListAction.OnBack -> {

            }
        }
    }
}