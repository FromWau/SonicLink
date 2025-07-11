package at.tfro.sonic_link.shared_client.importer.presentation.import_media

import androidx.lifecycle.ViewModel
import at.tfro.sonic_link.shared_client.importer.domain.repository.ImporterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ImportMediaViewModel(
    private val importerRepository: ImporterRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ImportMediaState())
    val state = _state.asStateFlow()

    fun onAction(action: ImportMediaAction) {
        when (action) {
            ImportMediaAction.OnBack -> {}
        }
    }
}
