package at.tfro.sonic_link.shared_client.importer.presentation.import_media

import at.tfro.sonic_link.shared_client.core.presentation.StringValue
import at.tfro.sonic_link.shared_client.importer.domain.model.ImportMedia

data class ImportMediaState(
    val error: StringValue? = null,
    val isLoading: Boolean = true,
    val media: ImportMedia? = null,
    val hits: List<Recording> = emptyList(),
)

data class Recording(
    val id: String,
    val score: Int,
    val artistCreditId: String,
    val title: String,
    val disambiguation: String? = null,
    val video: String? = null,
    val artistCredit: List<ArtistCredit>,
)

data class ArtistCredit(
    val name: String,
    val artist: Artist,
)

data class Artist(
    val id: String,
    val name: String,
    val sortName: String? = null,
    val disambiguation: String? = null,
)
