package at.tfro.sonic_link.logger

import platform.Foundation.NSLog

actual class PlatformLoggerFactory {
    actual fun create(): PlatformLogger {
        return object : PlatformLogger {
            override fun log(tag: String, logLevel: LogLevel, lazyMessage: () -> String) {
                NSLog(this.toLogString(tag, logLevel, lazyMessage))
            }

            override fun log(tag: String, logLevel: LogLevel, throwable: Throwable) {
                NSLog(this.toLogString(tag, logLevel, throwable))
            }
        }
    }
}
