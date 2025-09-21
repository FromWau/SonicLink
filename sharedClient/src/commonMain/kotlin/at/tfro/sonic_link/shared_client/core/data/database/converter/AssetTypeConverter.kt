package at.tfro.sonic_link.shared_client.core.data.database.converter

import androidx.room.TypeConverter
import at.tfro.sonic_link.shared_client.core.data.database.media.AssetType

class AssetTypeConverter {
    @TypeConverter
    fun fromAssetType(assetType: AssetType): String = assetType.toString()

    @TypeConverter
    fun toAssetType(assetTypeString: String): AssetType = AssetType.valueOf(assetTypeString)
}
