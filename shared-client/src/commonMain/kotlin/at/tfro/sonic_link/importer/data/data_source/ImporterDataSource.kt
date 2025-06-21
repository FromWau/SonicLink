package at.tfro.sonic_link.importer.data.data_source

import at.tfro.sonic_link.importer.data.model.PossibleMediaDto

interface ImporterDataSource {
    suspend fun getAllImportableMedia(): List<PossibleMediaDto>
}