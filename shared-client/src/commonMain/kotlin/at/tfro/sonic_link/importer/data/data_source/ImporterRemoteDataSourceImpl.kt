package at.tfro.sonic_link.importer.data.data_source

import at.tfro.sonic_link.importer.data.model.PossibleMediaDto
import at.tfro.sonic_link.importer.data.network.ImporterApiClient

class ImporterRemoteDataSourceImpl(
    private val client: ImporterApiClient,
) : ImporterDataSource {
    override suspend fun getAllImportableMedia(): List<PossibleMediaDto> {
        return client.getAllImportableMedia()
    }
}