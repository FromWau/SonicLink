package at.tfro.sonic_link.core.data.network

import at.tfro.sonic_link.utils.Logger
import at.tfro.sonic_link.utils.t
import at.tfro.sonic_link.utils.tag
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.Logger as KtorLogger

object HttpClientFactory {
    fun create(
        engine: HttpClientEngine,
        appLogger: Logger,
    ): HttpClient {
        return HttpClient(engine = engine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : KtorLogger {
                    override fun log(message: String) {
                        appLogger.tag("Ktor").t { message }
                    }
                }
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 15_000
            }

            defaultRequest {
                headers.append("Accept", "application/json")
            }
        }
    }
}