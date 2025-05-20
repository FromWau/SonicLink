package at.tfro.sonic_link.data

import at.tfro.sonic_link.Media
import at.tfro.sonic_link.db.dao.MediaDao
import at.tfro.sonic_link.db.dbo.MediaDbo
import java.util.UUID

class MediaRepositoryImpl(
    private val dao: MediaDao,
) : MediaRepository {
    override suspend fun upsert(media: Media) = dao.upsert(media = media.toDbo())

    override suspend fun getMediaById(id: UUID): Media? = dao.getMediaById(id)?.toModel()

    override suspend fun getAllMedia(): List<Media> = dao.getAllMedia().map { it.toModel() }
}

private fun Media.toDbo() = MediaDbo(
    id = this.id,
    name = this.name,
    path = this.path,
    mediaType = this.mediaType.toDbo(),
)

private fun Media.MediaType.toDbo(): MediaDbo.MediaType = when (this) {
    Media.MediaType.AUDIO -> MediaDbo.MediaType.AUDIO
    Media.MediaType.VIDEO -> MediaDbo.MediaType.VIDEO
}

private fun MediaDbo.toModel(): Media = Media(
    id = this.id,
    name = this.name,
    path = this.path,
    mediaType = this.mediaType.toModel(),
)

private fun MediaDbo.MediaType.toModel(): Media.MediaType = when (this) {
    MediaDbo.MediaType.AUDIO -> Media.MediaType.AUDIO
    MediaDbo.MediaType.VIDEO -> Media.MediaType.VIDEO
}