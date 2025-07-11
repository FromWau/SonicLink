package at.tfro.sonic_link.shared_client.importer.domain.repository

import at.tfro.sonic_link.shared_client.importer.domain.model.ImportMedia

interface ImporterRepository {
    suspend fun getAllImportableMedia(): List<ImportMedia>
}