package at.tfro.sonic_link.server.sync.data.mapper

import at.tfro.sonic_link.server.sync.data.model.AlbumEntity
import at.tfro.sonic_link.server.sync.data.model.AlbumWithRelations
import at.tfro.sonic_link.server.sync.data.model.ArtistEntity
import at.tfro.sonic_link.server.sync.data.model.RecordEntity
import at.tfro.sonic_link.server.sync.data.model.RecordWithRelations
import at.tfro.sonic_link.server.sync.data.model.SyncVersionEntity
import at.tfro.sonic_link.server.sync.domain.model.Album
import at.tfro.sonic_link.server.sync.domain.model.Artist
import at.tfro.sonic_link.server.sync.domain.model.Record
import at.tfro.sonic_link.server.sync.domain.model.SyncVersion

fun SyncVersion.toEntity(): SyncVersionEntity =
    SyncVersionEntity(
        version = this.version,
        releasedAt = this.releasedAt,
    )

fun SyncVersionEntity.toDomain(): SyncVersion =
    SyncVersion(
        version = this.version,
        releasedAt = this.releasedAt,
    )

fun Record.toEntity(): RecordEntity =
    RecordEntity(
        id = this.id,
        title = this.title,
        albumId = this.album.id,
        artistId = this.artist.id,
        path = this.path,
        syncVersionVersion = this.syncVersion.version,
        lastModified = this.syncVersion.releasedAt,
        isDeleted = this.isDeleted,
    )

fun RecordWithRelations.toDomain(): Record =
    Record(
        id = this.recordId,
        title = this.recordTitle,
        path = this.recordPath,
        syncVersion = SyncVersion(
            version = this.recordSyncVersionVersion,
            releasedAt = this.recordSyncVersionReleasedAt,
        ),
        lastModified = this.recordLastModified,
        isDeleted = this.recordIsDeleted,

        artist = Artist(
            id = this.artistId,
            name = this.artistName,
            coverArtPath = this.artistCoverArtPath,
            path = this.artistPath,
            syncVersion = SyncVersion(
                version = this.artistSyncVersionVersion,
                releasedAt = this.artistSyncVersionReleasedAt,
            ),
            lastModified = this.artistLastModified,
            isDeleted = this.artistIsDeleted,
        ),

        album = Album(
            id = this.albumId,
            title = this.albumTitle,
            coverArtPath = this.albumCoverArtPath,
            artist = Artist(
                id = this.albumArtistId,
                name = this.albumArtistName,
                coverArtPath = this.albumArtistCoverArtPath,
                path = this.albumArtistPath,
                syncVersion = SyncVersion(
                    version = this.albumArtistSyncVersionVersion,
                    releasedAt = this.albumArtistSyncVersionReleasedAt,
                ),
                lastModified = this.albumArtistLastModified,
                isDeleted = this.albumArtistIsDeleted,
            ),
            path = this.albumPath,
            syncVersion = SyncVersion(
                version = this.albumSyncVersionVersion,
                releasedAt = this.albumSyncVersionReleasedAt,
            ),
            lastModified = this.albumLastModified,
            isDeleted = this.albumIsDeleted,
        )
    )


fun Album.toEntity(): AlbumEntity =
    AlbumEntity(
        id = this.id,
        title = this.title,
        artistId = this.artist.id,
        coverArtPath = this.coverArtPath,
        path = this.path,
        syncVersionVersion = this.syncVersion.version,
        lastModified = this.syncVersion.releasedAt,
        isDeleted = this.isDeleted,
    )

fun AlbumWithRelations.toDomain(): Album =
    Album(
        id = this.album.id,
        title = this.album.title,
        artist = this.artist.toDomain(),
        coverArtPath = this.album.coverArtPath,
        path = this.album.path,
        syncVersion = this.syncVersion.toDomain(),
        isDeleted = this.album.isDeleted,
        lastModified = this.album.lastModified,
    )

fun Artist.toEntity() =
    ArtistEntity(
        id = this.id,
        name = this.name,
        coverArtPath = this.coverArtPath,
        path = this.path,
        syncVersionVersion = this.syncVersion.version,
        lastModified = this.syncVersion.releasedAt,
        isDeleted = this.isDeleted,
    )

fun ArtistEntity.toDomain() =
    Artist(
        id = this.id,
        name = this.name,
        coverArtPath = this.coverArtPath,
        path = this.path,
        syncVersion = SyncVersion(
            version = this.syncVersionVersion,
            releasedAt = this.lastModified,
        ),
        lastModified = this.lastModified,
        isDeleted = this.isDeleted,
    )
