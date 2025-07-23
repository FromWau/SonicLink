package at.tfro.sonic_link.server.sync.domain.repository

import at.tfro.sonic_link.server.sync.domain.model.Record

interface RecordRepository {
    suspend fun insertRecord(record: Record)
    suspend fun updateRecord(record: Record)
    suspend fun deleteRecord(record: Record)
    suspend fun getAllRecords(): List<Record>
    suspend fun clearRecords()
}