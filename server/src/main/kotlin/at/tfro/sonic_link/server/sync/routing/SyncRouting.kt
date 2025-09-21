package at.tfro.sonic_link.server.sync.routing

import at.tfro.sonic_link.core.logger.Logger
import at.tfro.sonic_link.shared_rpc.sync.SyncService
import io.ktor.server.routing.Route
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Route.syncRoutes() {
    val logger by inject<Logger>()

    rpc("/sync") {
        logger.tag("sync").i { "Sync endpoint triggered" }
        rpcConfig {
            serialization {
                json()
            }
        }

        registerService<SyncService> { get() }
    }
}
