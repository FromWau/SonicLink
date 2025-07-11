package at.tfro.sonic_link.server.data

import at.tfro.sonic_link.interim.Album

interface AlbumRepository {
    suspend fun upsert(album: Album): Album
}