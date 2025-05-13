package at.tfro.sonic_link.library.domain

data class Music(
    val id: String,
    val title: String,
    val album: String,
    val artists: List<String>,
    val albumArtUrl: String? = null,
)