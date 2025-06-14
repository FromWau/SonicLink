package at.tfro.sonic_link.importer.data.mapper

import at.tfro.sonic_link.importer.data.model.PossibleMediaDto
import at.tfro.sonic_link.importer.domain.model.ImportMedia

fun PossibleMediaDto.toDomain() = ImportMedia(
    path = path,
    title = possibleTitle,
    artist = possibleArtist,
    album = possibleAlbum,
)