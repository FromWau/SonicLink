package at.tfro.sonic_link.server.sync.domain.repository

import at.tfro.sonic_link.server.sync.domain.model.Artist

interface ArtistRepository {
    suspend fun insertArtist(artist: Artist)
    suspend fun updateArtist(artist: Artist)
    suspend fun deleteArtist(artist: Artist)
    suspend fun getAllArtists(): List<Artist>
    suspend fun clearArtists()
}