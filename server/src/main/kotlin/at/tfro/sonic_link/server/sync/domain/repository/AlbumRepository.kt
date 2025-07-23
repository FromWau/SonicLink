package at.tfro.sonic_link.server.sync.domain.repository

import at.tfro.sonic_link.server.sync.domain.model.Album

interface AlbumRepository {
    suspend fun insertAlbum(album: Album)
    suspend fun updateAlbum(album: Album)
    suspend fun deleteAlbum(album: Album)
    suspend fun getAllAlbums(): List<Album>
    suspend fun clearAlbums()
}