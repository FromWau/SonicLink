package at.tfro.sonic_link.server

import at.tfro.sonic_link.server.api.plugins.configureKoin
import at.tfro.sonic_link.server.api.plugins.configureSerialization
import at.tfro.sonic_link.server.api.plugins.configureTrailingSlashRedirect
import at.tfro.sonic_link.server.api.routes.configureRouting
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) = EngineMain.main(args)


fun Application.module() {
    configureKoin()
    configureSerialization()
    configureTrailingSlashRedirect()
    configureRouting()
}
