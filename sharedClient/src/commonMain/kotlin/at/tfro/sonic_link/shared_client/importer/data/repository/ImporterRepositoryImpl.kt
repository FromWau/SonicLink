package at.tfro.sonic_link.shared_client.importer.data.repository

import at.tfro.sonic_link.shared_client.importer.data.data_source.ImporterDataSource
import at.tfro.sonic_link.shared_client.importer.data.mapper.toDomain
import at.tfro.sonic_link.shared_client.importer.domain.model.ImportMedia
import at.tfro.sonic_link.shared_client.importer.domain.repository.ImporterRepository

class ImporterRepositoryImpl(
    private val dataSource: ImporterDataSource,
) : ImporterRepository {
    override suspend fun getAllImportableMedia(): List<ImportMedia> =
        dataSource.getAllImportableMedia().map { it.toDomain() }
}