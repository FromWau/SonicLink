package at.tfro.sonic_link.importer.domain.repository

import at.tfro.sonic_link.importer.domain.model.ImportMedia

interface ImporterRepository {
    suspend fun getAllImportableMedia(): List<ImportMedia>
}