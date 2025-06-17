package at.tfro.sonic_link.importer.presentation.import_media

import at.tfro.sonic_link.core.presentation.StringValue
import at.tfro.sonic_link.importer.domain.model.ImportMedia

data class ImportMediaState(
    val error: StringValue? = null,
    val isLoading: Boolean = true,
    val media: ImportMedia? = null
)