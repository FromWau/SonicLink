package at.tfro.sonic_link.shared_client.importer.data.data_source

import at.tfro.sonic_link.shared_client.importer.data.model.PossibleMediaDto

interface ImporterDataSource {
    suspend fun getAllImportableMedia(): List<PossibleMediaDto>
}