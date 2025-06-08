package at.tfro.sonic_link.importer.presentation

import at.tfro.sonic_link.core.presentation.StringValue
import at.tfro.sonic_link.importer.domain.ImportMedia

data class ImporterState(
    val error: StringValue? = null,
    val isLoading: Boolean = false,
    val mediaToImport: List<ImportMedia> = emptyList(),
    val identifiedMedia: List<ImportMedia> = emptyList(),
    val mediaToIdentify: ImportMedia? = null,
)