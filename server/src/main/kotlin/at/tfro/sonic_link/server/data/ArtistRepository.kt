package at.tfro.sonic_link.server.data

import at.tfro.sonic_link.interim.Artist

interface ArtistRepository {
    suspend fun upsert(artist: Artist): Artist
}