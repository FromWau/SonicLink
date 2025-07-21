package at.tfro.sonic_link.server.sync.data

import at.tfro.sonic_link.server.sync.data.database.dao.AlbumDao
import at.tfro.sonic_link.server.sync.data.database.dao.ArtistDao
import at.tfro.sonic_link.server.sync.data.database.dao.RecordDao
import at.tfro.sonic_link.server.sync.data.database.dao.SyncVersionDao
import at.tfro.sonic_link.server.sync.data.mapper.toDomain
import at.tfro.sonic_link.server.sync.data.mapper.toEntity
import at.tfro.sonic_link.server.sync.data.model.SyncVersionEntity
import at.tfro.sonic_link.server.sync.domain.model.Album
import at.tfro.sonic_link.server.sync.domain.model.Artist
import at.tfro.sonic_link.server.sync.domain.model.Record
import at.tfro.sonic_link.server.sync.domain.model.SyncVersion
import at.tfro.sonic_link.server.sync.domain.repository.SyncRepository
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class SyncRepositoryImpl(
    private val syncVersionDao: SyncVersionDao,
    private val artistDao: ArtistDao,
    private val albumDao: AlbumDao,
    private val recordDao: RecordDao,
) : SyncRepository {
    override suspend fun getAllSyncVersions(): List<SyncVersion> =
        syncVersionDao.getAllSyncVersions().map { it.toDomain() }

    override suspend fun findByVersion(version: Long): SyncVersion? =
        syncVersionDao.findByVersion(version)?.toDomain()

    override fun getCurrentSyncVersionFlow() =
        syncVersionDao.getCurrentSyncVersionFlow().map { it?.toDomain() }

    override suspend fun generateDelta(
        currentVersion: SyncVersion,
        targetVersion: SyncVersion,
    ): SyncVersion {
        TODO("Not yet implemented")
    }

    override suspend fun versionBump(): SyncVersion {
        return syncVersionDao.insertGetVersion(
            SyncVersionEntity(
                releasedAt = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            )
        )
            ?.toDomain()
            ?: throw IllegalStateException("Failed to create new sync version")
    }

    override suspend fun insertRecord(record: Record) {
        syncVersionDao.insert(record.artist.syncVersion.toEntity())
        artistDao.insert(record.artist.toEntity())
        syncVersionDao.insert(record.album.syncVersion.toEntity())
        albumDao.insert(record.album.toEntity())
        syncVersionDao.insert(record.syncVersion.toEntity())
        recordDao.insert(record.toEntity())
    }
    override suspend fun updateRecord(record: Record) = recordDao.update(record.toEntity())
    override suspend fun deleteRecord(record: Record) = recordDao.delete(record.toEntity())
    override suspend fun getAllRecords(): List<Record> = recordDao.getAllRecordsWithAlbumAndArtist().map { it.toDomain() }

    override suspend fun insertAlbum(album: Album) {
        syncVersionDao.insert(album.artist.syncVersion.toEntity())
        artistDao.insert(album.artist.toEntity())
        syncVersionDao.insert(album.syncVersion.toEntity())
        albumDao.insert(album.toEntity())
    }
    override suspend fun updateAlbum(album: Album) = albumDao.update(album.toEntity())
    override suspend fun deleteAlbum(album: Album) = albumDao.delete(album.toEntity())
    override suspend fun getAllAlbums(): List<Album> = albumDao.getAll().map { it.toDomain() }

    override suspend fun insertArtist(artist: Artist) = artistDao.insert(artist.toEntity())
    override suspend fun updateArtist(artist: Artist) = artistDao.update(artist.toEntity())
    override suspend fun deleteArtist(artist: Artist) = artistDao.delete(artist.toEntity())
    override suspend fun getAllArtists(): List<Artist> = artistDao.getAll().map { it.toDomain() }

    override suspend fun clearAllData() {
        syncVersionDao.clear()
        recordDao.clear()
        albumDao.clear()
        artistDao.clear()

        // Re-insert the initial sync version after clearing
        syncVersionDao.insertGetVersion(
            SyncVersionEntity(
                version = 1L,
                releasedAt = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            )
        )
    }
}