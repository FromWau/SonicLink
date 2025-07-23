package at.tfro.sonic_link.server.sync.domain.model

import kotlinx.datetime.Instant
import kotlin.uuid.Uuid

data class GlobalChangeLog(
    val id: Long = 0,
    val version: Long,
    val model: String,
    val entityId: Uuid,
    val operation: DeltaOperation,
    val timestamp: Instant,
) {
    fun toPrettyString(preSpacing: String = ""): String {
        return "GlobalChangeLog {\n" +
                "$preSpacing  id=$id,\n" +
                "$preSpacing  version=$version,\n" +
                "$preSpacing  model=$model,\n" +
                "$preSpacing  entityId=$entityId,\n" +
                "$preSpacing  operation=$operation,\n" +
                "$preSpacing  timestamp=$timestamp\n" +
                "$preSpacing}"
    }
}