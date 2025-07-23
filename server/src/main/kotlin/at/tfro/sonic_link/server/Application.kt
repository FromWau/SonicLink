package at.tfro.sonic_link.server

import at.tfro.sonic_link.server.api.plugins.configureKoin
import at.tfro.sonic_link.server.api.plugins.configureSerialization
import at.tfro.sonic_link.server.api.plugins.configureTrailingSlashRedirect
import at.tfro.sonic_link.server.api.routes.configureRouting
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.cors.routing.CORS
import kotlinx.rpc.krpc.ktor.server.Krpc

fun main(args: Array<String>) = EngineMain.main(args)


fun Application.module() {
    configureKoin()
    install(Krpc)
    installCORS()
    configureSerialization()
    configureTrailingSlashRedirect()
    configureRouting()
}

fun Application.installCORS() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.Upgrade)
        allowNonSimpleContentTypes = true
        allowCredentials = true
        allowSameOrigin = true

        // webpack-dev-server and local development
        val allowedHosts =
            listOf("localhost:3000", "localhost:8080", "localhost:8081", "127.0.0.1:8080")
        allowedHosts.forEach { host ->
            allowHost(host, listOf("http", "https", "ws", "wss"))
        }
    }
}
