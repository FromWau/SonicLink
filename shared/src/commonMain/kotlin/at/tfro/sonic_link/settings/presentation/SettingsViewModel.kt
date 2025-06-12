package at.tfro.sonic_link.settings.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(

) : ViewModel() {
    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnServerSettingsChanged -> {
                _state.update {
                    it.copy(
                        serverSettings = it.serverSettings.copy(
                            baseUrl = action.baseUrl
                        )
                    )
                }
            }

            SettingsAction.OnBack -> {}
        }
    }
}