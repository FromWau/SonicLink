package at.tfro.sonic_link.server.sync.data.mapper

import at.tfro.sonic_link.server.sync.data.model.AlbumEntity
import at.tfro.sonic_link.server.sync.data.model.AlbumWithArtistEntity
import at.tfro.sonic_link.server.sync.data.model.ArtistEntity
import at.tfro.sonic_link.server.sync.data.model.RecordEntity
import at.tfro.sonic_link.server.sync.data.model.RecordWithAlbumAndArtistEntity
import at.tfro.sonic_link.server.sync.data.model.SyncVersionEntity
import at.tfro.sonic_link.server.sync.domain.model.Album
import at.tfro.sonic_link.server.sync.domain.model.Artist
import at.tfro.sonic_link.server.sync.domain.model.Record
import at.tfro.sonic_link.server.sync.domain.model.SyncVersion

fun SyncVersion.toEntity(): SyncVersionEntity =
    SyncVersionEntity(
        updatedAt = this.updatedAt,
    )

fun SyncVersionEntity.toDomain(): SyncVersion =
    SyncVersion(
        version = this.id,
        updatedAt = this.updatedAt,
    )

fun Record.toEntity(): RecordEntity =
    RecordEntity(
        id = this.id,
        title = this.title,
        albumId = this.album.id,
        artistId = this.artist.id,
        path = this.path,
    )

fun RecordWithAlbumAndArtistEntity.toDomain(): Record =
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

fun AlbumWithArtistEntity.toDomain(): Album =
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
