package at.tfro.sonic_link.importer.data.repository

import at.tfro.sonic_link.importer.data.data_source.ImporterDataSource
import at.tfro.sonic_link.importer.data.model.PossibleMediaDto
import at.tfro.sonic_link.importer.domain.model.ImportMedia
import at.tfro.sonic_link.importer.domain.repository.ImporterRepository

class ImporterRepositoryImpl(
    private val dataSource: ImporterDataSource,
) : ImporterRepository {
    override suspend fun getAllImportableMedia(): List<ImportMedia> {
        return dataSource.getAllImportableMedia().let {
            TODO("Create a mapper to convert PossibleMediaDto to ImportMedia")
        }
    }
}