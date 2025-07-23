package at.tfro.sonic_link.server.sync.data

import androidx.room.Transaction
import at.tfro.sonic_link.server.sync.data.database.dao.AlbumChangeLogDao
import at.tfro.sonic_link.server.sync.data.database.dao.AlbumDao
import at.tfro.sonic_link.server.sync.data.database.dao.GlobalChangeLogDao
import at.tfro.sonic_link.server.sync.data.database.dao.SyncVersionDao
import at.tfro.sonic_link.server.sync.data.mapper.toDomain
import at.tfro.sonic_link.server.sync.data.mapper.toEntity
import at.tfro.sonic_link.server.sync.data.model.AlbumChangeLogEntity
import at.tfro.sonic_link.server.sync.data.model.DeltaOperationEntity
import at.tfro.sonic_link.server.sync.data.model.GlobalChangeLogEntity
import at.tfro.sonic_link.server.sync.domain.model.Album
import at.tfro.sonic_link.server.sync.domain.repository.AlbumRepository
import kotlinx.datetime.Clock

class AlbumRepositoryImpl(
    private val albumDao: AlbumDao,
    private val albumChangeLogDao: AlbumChangeLogDao,
    private val syncVersionDao: SyncVersionDao,
    private val globalChangeLogDao: GlobalChangeLogDao,
) : AlbumRepository {

    @Transaction
    override suspend fun insertAlbum(album: Album) {
        val now = Clock.System.now()
        val entity = album.toEntity()

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

    override suspend fun updateAlbum(album: Album) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlbum(album: Album) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllAlbums(): List<Album> =
        albumDao.getAll().map { it.toDomain() }

    override suspend fun clearAlbums() {
        TODO("Not yet implemented")
    }
}