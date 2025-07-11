package at.tfro.sonic_link.shared_client.importer.data.mapper

import at.tfro.sonic_link.shared_client.importer.data.model.PossibleMediaDto
import at.tfro.sonic_link.shared_client.importer.domain.model.ImportMedia

fun PossibleMediaDto.toDomain() = ImportMedia(
    path = path,
    title = possibleTitle,
    artist = possibleArtist,
    album = possibleAlbum,
)