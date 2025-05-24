package at.tfro.sonic_link

import at.tfro.sonic_link.api.plugins.configureKoin
import at.tfro.sonic_link.api.plugins.configureSerialization
import at.tfro.sonic_link.api.plugins.configureTrailingSlashRedirect
import at.tfro.sonic_link.api.routes.configureRouting
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) = EngineMain.main(args)


fun Application.module() {
    configureKoin()
    configureSerialization()
    configureTrailingSlashRedirect()
    configureRouting()
}
