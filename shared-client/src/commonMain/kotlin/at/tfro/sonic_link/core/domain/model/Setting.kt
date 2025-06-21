package at.tfro.sonic_link.core.domain.model

import kotlin.uuid.Uuid

data class Setting(
    val id: Uuid,
    val host: String,
    val isActive: Boolean,
)