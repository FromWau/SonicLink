package at.tfro.sonic_link.logger

class DebugLogger(private val platformLogger: PlatformLogger) : Logger {
    private val logs = mutableListOf<String>()

    override fun log(tag: String, logLevel: LogLevel, lazyMessage: () -> String) {
        val logMsg = this.toLogString(tag, logLevel, lazyMessage)
        logs += logMsg
        platformLogger.log(tag, logLevel, lazyMessage)
    }

    override fun log(tag: String, logLevel: LogLevel, throwable: Throwable) {
        val logMsg = this.toLogString(tag, logLevel, throwable)
        logs += logMsg
        platformLogger.log(tag, logLevel, throwable)
    }
}
