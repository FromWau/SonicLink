package at.tfro.sonic_link.importer.domain.repository

interface ImporterRepository {
    suspend fun getAllImportableMedia(): List<String>
}