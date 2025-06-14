package at.tfro.sonic_link.settings.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(

) : ViewModel() {
    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.OnBack -> Unit
            is SettingsAction.OnAddServer -> TODO()
            is SettingsAction.OnRemoveServer -> TODO()
        }
    }
}