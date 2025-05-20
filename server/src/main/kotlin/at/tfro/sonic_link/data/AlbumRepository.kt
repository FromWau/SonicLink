package at.tfro.sonic_link.data

import at.tfro.sonic_link.Album

interface AlbumRepository {
    suspend fun upsert(album: Album): Album
}