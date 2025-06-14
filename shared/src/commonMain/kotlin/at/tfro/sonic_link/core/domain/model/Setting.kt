package at.tfro.sonic_link.core.domain.model

data class Setting(
    val id: Long,
    val host: String,
    val isActive: Boolean,
)