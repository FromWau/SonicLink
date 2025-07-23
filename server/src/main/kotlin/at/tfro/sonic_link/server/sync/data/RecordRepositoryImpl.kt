package at.tfro.sonic_link.server.sync.data

import androidx.room.Transaction
import at.tfro.sonic_link.core.logger.Logger
import at.tfro.sonic_link.core.logger.i
import at.tfro.sonic_link.core.logger.tag
import at.tfro.sonic_link.server.sync.data.database.dao.AlbumChangeLogDao
import at.tfro.sonic_link.server.sync.data.database.dao.AlbumDao
import at.tfro.sonic_link.server.sync.data.database.dao.ArtistChangeLogDao
import at.tfro.sonic_link.server.sync.data.database.dao.ArtistDao
import at.tfro.sonic_link.server.sync.data.database.dao.GlobalChangeLogDao
import at.tfro.sonic_link.server.sync.data.database.dao.RecordChangeLogDao
import at.tfro.sonic_link.server.sync.data.database.dao.RecordDao
import at.tfro.sonic_link.server.sync.data.database.dao.SyncVersionDao
import at.tfro.sonic_link.server.sync.data.mapper.toDomain
import at.tfro.sonic_link.server.sync.data.mapper.toEntity
import at.tfro.sonic_link.server.sync.data.model.AlbumChangeLogEntity
import at.tfro.sonic_link.server.sync.data.model.ArtistChangeLogEntity
import at.tfro.sonic_link.server.sync.data.model.DeltaOperationEntity
import at.tfro.sonic_link.server.sync.data.model.GlobalChangeLogEntity
import at.tfro.sonic_link.server.sync.data.model.RecordChangeLogEntity
import at.tfro.sonic_link.server.sync.domain.model.Album
import at.tfro.sonic_link.server.sync.domain.model.Artist
import at.tfro.sonic_link.server.sync.domain.model.Record
import at.tfro.sonic_link.server.sync.domain.repository.RecordRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class RecordRepositoryImpl(
    private val recordDao: RecordDao,
    private val recordChangeLogDao: RecordChangeLogDao,
    private val albumDao: AlbumDao,
    private val albumChangeLogDao: AlbumChangeLogDao,
    private val artistDao: ArtistDao,
    private val artistChangeLogDao: ArtistChangeLogDao,
    private val syncVersionDao: SyncVersionDao,
    private val globalChangeLogDao: GlobalChangeLogDao,
    private val logger: Logger,
) : RecordRepository {

    @Transaction
    override suspend fun insertRecord(record: Record) {
        val now = Clock.System.now()
        val entity = record.toEntity()

        // 0. Insert related entities if they do not exist
        insertArtist(record.artist, now)
        insertAlbum(record.album, now)

        val exists = recordDao.getByPath(record.path)
        if (exists != null) {
            logger.tag("recordDao")
                .i { "Record with Path ${record.path} already exists, skipping insert." }
            return
        }

        // 1. Insert the record entity
        recordDao.insert(entity)

        // 2. Log in the record-specific changelog
        recordChangeLogDao.insert(
            RecordChangeLogEntity(
                recordId = entity.id,
                operation = DeltaOperationEntity.CREATE,
                timestamp = now,
            )
        )

        // 3. Get next version and log in the global changelog
        val version = syncVersionDao.getNextVersion().version

        globalChangeLogDao.insert(
            GlobalChangeLogEntity(
                version = version,
                model = "Record",
                entityId = record.id,
                operation = DeltaOperationEntity.CREATE,
                timestamp = now,
            )
        )
    }

    @Transaction
    suspend fun insertAlbum(album: Album, now: Instant) {
        val entity = album.toEntity()

        val exists = albumDao.getByPath(album.path)
        if (exists != null) {
            logger.tag("recordDao")
                .i { "Album with Path ${album.path} already exists, skipping insert." }
            return
        }

        // 1. Insert the album entity
        albumDao.insert(entity)

        // 2. Log in the album-specific changelog
        albumChangeLogDao.insert(
            AlbumChangeLogEntity(
                albumId = entity.id,
                operation = DeltaOperationEntity.CREATE,
                timestamp = now,
            )
        )

        // 3. Get next version and log in the global changelog
        val version = syncVersionDao.getNextVersion().version

        globalChangeLogDao.insert(
            GlobalChangeLogEntity(
                version = version,
                model = "Album",
                entityId = album.id,
                operation = DeltaOperationEntity.CREATE,
                timestamp = now,
            )
        )
    }

    @Transaction
    suspend fun insertArtist(artist: Artist, now: Instant) {
        val entity = artist.toEntity()

        logger.tag("recordDao").i { "Inserting artist entity: ${artist.toPrettyString()}" }

        val exists = artistDao.getByPath(artist.path)
        if (exists != null) {
            logger.tag("recordDao")
                .i { "Artist with Path ${artist.path} already exists, skipping insert." }
            return
        }

        // 1. Insert the artist entity
        artistDao.insert(entity)

        // 2. Log in the artist-specific changelog
        artistChangeLogDao.insert(
            ArtistChangeLogEntity(
                artistId = entity.id,
                operation = DeltaOperationEntity.CREATE,
                timestamp = now
            )
        )

        // 3. Get next version and log in the global changelog
        val version = syncVersionDao.getNextVersion().version

        globalChangeLogDao.insert(
            GlobalChangeLogEntity(
                version = version,
                model = "Artist",
                entityId = artist.id,
                operation = DeltaOperationEntity.CREATE,
                timestamp = now,
            )
        )
    }

    override suspend fun updateRecord(record: Record) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecord(record: Record) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRecords(): List<Record> =
        recordDao.getAllRecordsWithAlbumAndArtist().map { it.toDomain() }

    override suspend fun clearRecords() {
        TODO("Not yet implemented")
    }
}