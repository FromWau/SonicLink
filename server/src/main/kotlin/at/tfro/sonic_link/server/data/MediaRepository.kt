package at.tfro.sonic_link.server.data

import at.tfro.sonic_link.interim.Media
import java.util.UUID

interface MediaRepository {
    suspend fun upsert(media: Media): Unit

    suspend fun getMediaById(id: UUID): Media?

    suspend fun getAllMedia(): List<Media>
}