package at.tfro.sonic_link.shared_client.player.presentation

import at.tfro.sonic_link.shared_client.core.data.database.media.MediaWithAssets

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentModel: Record? = null,
    val queue: List<Record>? = null,
    val history: List<Record>? = null,
)


data class Record(
    val mediaWithAssets: MediaWithAssets,
    val selectedAssetIndex: Int = 0,
)