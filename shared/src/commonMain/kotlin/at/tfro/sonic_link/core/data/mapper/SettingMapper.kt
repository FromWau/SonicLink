package at.tfro.sonic_link.core.data.mapper

import at.tfro.sonic_link.core.data.database.SettingEntity
import at.tfro.sonic_link.core.domain.model.Setting

fun Setting.toSettingEntity(): SettingEntity {
    return SettingEntity(baseUrl = this.baseUrl)
}


fun SettingEntity.toDomain(): Setting {
    return Setting(baseUrl = this.baseUrl)
}