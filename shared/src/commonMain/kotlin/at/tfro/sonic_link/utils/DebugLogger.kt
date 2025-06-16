package at.tfro.sonic_link.utils

import kotlinx.datetime.Clock

class DebugLogger(private val platformLogger: PlatformLogger) : Logger {
    private val logs = mutableListOf<String>()

    override fun log(tag: String, logLevel: LogLevel, lazyMessage: () -> String) {
        logs += "${Clock.System.now()} [${tag}] ${logLevel.name}: ${lazyMessage()}"
        platformLogger.log(tag, logLevel, lazyMessage)
    }

    override fun log(tag: String, logLevel: LogLevel, throwable: Throwable) {
        logs += "${Clock.System.now()} [${tag}] ${logLevel.name}: ${throwable.message}"
        platformLogger.log(tag, logLevel, throwable)
    }
}
