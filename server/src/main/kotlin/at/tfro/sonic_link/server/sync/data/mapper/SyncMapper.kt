package at.tfro.sonic_link.server.sync.data.mapper

import at.tfro.sonic_link.server.sync.data.model.AlbumEntity
import at.tfro.sonic_link.server.sync.data.model.AlbumWithRelations
import at.tfro.sonic_link.server.sync.data.model.ArtistEntity
import at.tfro.sonic_link.server.sync.data.model.DeltaOperationEntity
import at.tfro.sonic_link.server.sync.data.model.GlobalChangeLogEntity
import at.tfro.sonic_link.server.sync.data.model.RecordEntity
import at.tfro.sonic_link.server.sync.data.model.RecordWithRelations
import at.tfro.sonic_link.server.sync.domain.model.Album
import at.tfro.sonic_link.server.sync.domain.model.Artist
import at.tfro.sonic_link.server.sync.domain.model.DeltaOperation
import at.tfro.sonic_link.server.sync.domain.model.GlobalChangeLog
import at.tfro.sonic_link.server.sync.domain.model.Record

fun Record.toEntity(): RecordEntity =
    RecordEntity(
        id = this.id,
        title = this.title,
        albumId = this.album.id,
        artistId = this.artist.id,
        path = this.path,
    )

fun RecordWithRelations.toDomain(): Record =
    Record(
        id = this.recordId,
        title = this.recordTitle,
        path = this.recordPath,
        artist = Artist(
            id = this.artistId,
            name = this.artistName,
            coverArtPath = this.artistCoverArtPath,
            path = this.artistPath,
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
            ),
            path = this.albumPath,
        )
    )

fun Album.toEntity(): AlbumEntity =
    AlbumEntity(
        id = this.id,
        title = this.title,
        artistId = this.artist.id,
        coverArtPath = this.coverArtPath,
        path = this.path,
    )

fun AlbumWithRelations.toDomain(): Album =
    Album(
        id = this.album.id,
        title = this.album.title,
        artist = this.artist.toDomain(),
        coverArtPath = this.album.coverArtPath,
        path = this.album.path,
    )

fun Artist.toEntity() =
    ArtistEntity(
        id = this.id,
        name = this.name,
        coverArtPath = this.coverArtPath,
        path = this.path,
    )

fun ArtistEntity.toDomain() =
    Artist(
        id = this.id,
        name = this.name,
        coverArtPath = this.coverArtPath,
        path = this.path,
    )

fun GlobalChangeLogEntity.toDomain() =
    GlobalChangeLog(
        id = this.id,
        version = this.version,
        model = this.model,
        entityId = this.entityId,
        operation = this.operation.toDomain(),
        timestamp = this.timestamp,
    )

fun DeltaOperationEntity.toDomain(): DeltaOperation = when (this) {
    DeltaOperationEntity.CREATE -> DeltaOperation.CREATE
    DeltaOperationEntity.UPDATE -> DeltaOperation.UPDATE
    DeltaOperationEntity.DELETE -> DeltaOperation.DELETE
}
