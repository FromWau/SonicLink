package at.tfro.sonic_link.shared_client.importer.presentation.importer_list

import at.tfro.sonic_link.shared_client.core.presentation.StringValue
import at.tfro.sonic_link.shared_client.importer.domain.model.ImportMedia

data class ImportListState(
    val error: StringValue? = null,
    val isLoading: Boolean = true,
    val mediaToImport: List<ImportMedia> = emptyList(),
    val identifiedMedia: List<ImportMedia> = emptyList(),
    val mediaToIdentify: ImportMedia? = null,
)