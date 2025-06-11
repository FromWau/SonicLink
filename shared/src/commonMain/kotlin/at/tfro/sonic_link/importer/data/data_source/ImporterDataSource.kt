package at.tfro.sonic_link.importer.data.data_source

interface ImporterDataSource {
    suspend fun getAllImportableMedia(): List<String>
}