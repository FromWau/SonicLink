package at.tfro.sonic_link.shared_rpc.sync

import at.tfro.sonic_link.shared_rpc.sync.model.SyncRequest
import at.tfro.sonic_link.shared_rpc.sync.model.SyncResponse
import at.tfro.sonic_link.shared_rpc.sync.model.SyncVersionRpc
import kotlinx.coroutines.flow.Flow
import kotlinx.rpc.annotations.Rpc

@Rpc
interface SyncService {
    fun subscribeToCurrentVersion(): Flow<SyncVersionRpc?>
    suspend fun sync(request: SyncRequest): SyncResponse

    suspend fun update(): SyncVersionRpc
}
