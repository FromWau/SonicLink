package at.tfro.sonic_link.api.plugins

import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            json = Json {
                this.prettyPrint = true
                this.ignoreUnknownKeys = true
                this.allowStructuredMapKeys = true
                this.isLenient = true
            },
            contentType = ContentType.Application.Json,
        )
    }
}