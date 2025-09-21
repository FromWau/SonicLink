package at.tfro.sonic_link.shared_client.core.data.database.media

import androidx.room.Embedded
import androidx.room.Relation

data class MediaWithAssets(
    @Embedded
    val media: MediaEntity,

    @Relation(
        parentColumn = "uuid",
        entityColumn = "media_uuid"
    )
    val assets: List<AssetEntity>,
)
