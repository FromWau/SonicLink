package at.tfro.sonic_link.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.tfro.sonic_link.core.domain.model.Setting
import at.tfro.sonic_link.core.domain.repository.SettingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingRepo: SettingRepository,
) : ViewModel() {
    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState())
    val state = _state
        .onStart { observeSettings() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.OnBack -> Unit
            is SettingsAction.OnAddServer -> viewModelScope.launch {
                settingRepo.upsert(
                    setting = Setting(
                        id = 0L,
                        host = action.host,
                        isActive = false,
                    )
                )
            }

            is SettingsAction.OnRemoveServer -> {
                viewModelScope.launch {
                    settingRepo.delete(action.setting)
                }
            }

            is SettingsAction.OnDeselectServer -> {
                viewModelScope.launch {
                    settingRepo.upsert(
                        action.setting.copy(isActive = false)
                    )
                }
            }

            is SettingsAction.OnSelectServer -> {
                viewModelScope.launch {
                    settingRepo.upsert(
                        action.setting.copy(isActive = true)
                    )
                }
            }

            is SettingsAction.OnUpdateServer -> {
                viewModelScope.launch {
                    settingRepo.upsert(action.setting)
                }
            }
        }
    }

    private fun observeSettings() {
        settingRepo.getAllSettings()
            .onEach { settings ->
                _state.update {
                    it.copy(settings = settings)
                }
            }
            .launchIn(viewModelScope)
    }
}