package at.tfro.sonic_link.core.data.mapper

import at.tfro.sonic_link.core.data.database.SettingEntity
import at.tfro.sonic_link.core.domain.model.Setting

fun Setting.toSettingEntity(): SettingEntity =
    SettingEntity(id = this.id, host = this.host, isActive = this.isActive)

fun SettingEntity.toDomain(): Setting =
    Setting(id = this.id, host = this.host, isActive = this.isActive)
