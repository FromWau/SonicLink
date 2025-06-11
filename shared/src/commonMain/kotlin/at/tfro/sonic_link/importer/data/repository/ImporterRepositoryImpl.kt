package at.tfro.sonic_link.importer.data.repository

import at.tfro.sonic_link.importer.data.data_source.ImporterDataSource
import at.tfro.sonic_link.importer.domain.repository.ImporterRepository

class ImporterRepositoryImpl(
    private val dataSource: ImporterDataSource,
) : ImporterRepository {
    override suspend fun getAllImportableMedia(): List<String> {
        return dataSource.getAllImportableMedia()
    }
}