package at.tfro.sonic_link.server.sync.domain.repository

import at.tfro.sonic_link.server.sync.domain.model.Album
import at.tfro.sonic_link.server.sync.domain.model.Artist
import at.tfro.sonic_link.server.sync.domain.model.Record
import at.tfro.sonic_link.server.sync.domain.model.SyncVersion
import kotlinx.coroutines.flow.Flow

interface SyncRepository {
    suspend fun getAllSyncVersions(): List<SyncVersion>
    suspend fun findByVersion(version: Long): SyncVersion?

    fun getCurrentSyncVersionFlow(): Flow<SyncVersion?>
    suspend fun generateDelta(currentVersion: SyncVersion, targetVersion: SyncVersion): SyncVersion

    suspend fun versionBump(): SyncVersion


    suspend fun insertRecord(record: Record)
    suspend fun updateRecord(record: Record)
    suspend fun deleteRecord(record: Record)
    suspend fun getAllRecords(): List<Record>

    suspend fun insertAlbum(album: Album)
    suspend fun updateAlbum(album: Album)
    suspend fun deleteAlbum(album: Album)
    suspend fun getAllAlbums(): List<Album>

    suspend fun insertArtist(artist: Artist)
    suspend fun updateArtist(artist: Artist)
    suspend fun deleteArtist(artist: Artist)
    suspend fun getAllArtists(): List<Artist>

    suspend fun clearAllData()
}
