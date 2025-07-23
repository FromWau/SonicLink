package at.tfro.sonic_link.server.sync.data

import androidx.room.Transaction
import at.tfro.sonic_link.server.sync.data.database.dao.ArtistChangeLogDao
import at.tfro.sonic_link.server.sync.data.database.dao.ArtistDao
import at.tfro.sonic_link.server.sync.data.database.dao.GlobalChangeLogDao
import at.tfro.sonic_link.server.sync.data.database.dao.SyncVersionDao
import at.tfro.sonic_link.server.sync.data.mapper.toDomain
import at.tfro.sonic_link.server.sync.data.mapper.toEntity
import at.tfro.sonic_link.server.sync.data.model.ArtistChangeLogEntity
import at.tfro.sonic_link.server.sync.data.model.DeltaOperationEntity
import at.tfro.sonic_link.server.sync.data.model.GlobalChangeLogEntity
import at.tfro.sonic_link.server.sync.domain.model.Artist
import at.tfro.sonic_link.server.sync.domain.repository.ArtistRepository
import kotlinx.datetime.Clock

class ArtistRepositoryImpl(
    private val artistDao: ArtistDao,
    private val artistChangeLogDao: ArtistChangeLogDao,
    private val syncVersionDao: SyncVersionDao,
    private val globalChangeLogDao: GlobalChangeLogDao,
) : ArtistRepository {

    @Transaction
    override suspend fun insertArtist(artist: Artist) {
        val now = Clock.System.now()
        val entity = artist.toEntity()

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

    override suspend fun updateArtist(artist: Artist) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteArtist(artist: Artist) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllArtists(): List<Artist> =
        artistDao.getAll().map { it.toDomain() }

    override suspend fun clearArtists() {
        TODO("Not yet implemented")
    }
}