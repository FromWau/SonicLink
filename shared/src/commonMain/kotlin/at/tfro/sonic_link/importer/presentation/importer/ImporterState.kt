package at.tfro.sonic_link.importer.presentation.importer

import at.tfro.sonic_link.core.presentation.StringValue
import at.tfro.sonic_link.importer.domain.ImportMedia

data class ImporterState(
    val isLoading: Boolean = false,
    val importableMedia: List<ImportMedia> = emptyList(),
    val selectedMedia: List<ImportMedia> = emptyList(),
    val selectedTabIndex: Int = 0,
    val searchQuery: String = "",
    val error: StringValue? = null,
)