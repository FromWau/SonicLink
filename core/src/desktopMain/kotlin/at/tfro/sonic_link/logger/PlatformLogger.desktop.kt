package at.tfro.sonic_link.logger

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformLogger actual constructor() : Logger {
    override fun log(tag: String, logLevel: LogLevel, lazyMessage: () -> String) {
        when (logLevel) {
            LogLevel.TRACE -> println("[$tag] TRACE: ${lazyMessage()}")
            LogLevel.DEBUG -> println("[$tag] DEBUG: ${lazyMessage()}")
            LogLevel.INFO -> println("[$tag] INFO: ${lazyMessage()}")
            LogLevel.WARN -> println("[$tag] WARN: ${lazyMessage()}")
            LogLevel.ERROR -> println("[$tag] ERROR: ${lazyMessage()}")
        }
    }

    override fun log(tag: String, logLevel: LogLevel, throwable: Throwable) {
        when (logLevel) {
            LogLevel.TRACE -> println("[$tag] TRACE: ${throwable.message}")
            LogLevel.DEBUG -> println("[$tag] DEBUG: ${throwable.message}")
            LogLevel.INFO -> println("[$tag] INFO: ${throwable.message}")
            LogLevel.WARN -> println("[$tag] WARN: ${throwable.message}")
            LogLevel.ERROR -> println("[$tag] ERROR: ${throwable.message}")
        }
    }
}