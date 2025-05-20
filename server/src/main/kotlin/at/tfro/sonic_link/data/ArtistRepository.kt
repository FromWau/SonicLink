package at.tfro.sonic_link.data

import at.tfro.sonic_link.Artist

interface ArtistRepository {
    suspend fun upsert(artist: Artist): Artist
}