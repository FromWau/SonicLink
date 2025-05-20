package at.tfro.sonic_link.data

import at.tfro.sonic_link.Media
import java.util.UUID

interface MediaRepository {
    suspend fun upsert(media: Media): Unit

    suspend fun getMediaById(id: UUID): Media?

    suspend fun getAllMedia(): List<Media>
}