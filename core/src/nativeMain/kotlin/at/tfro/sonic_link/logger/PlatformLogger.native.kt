package at.tfro.sonic_link.logger

import platform.Foundation.NSLog

actual class PlatformLogger actual constructor() : Logger {
    override fun log(tag: String, logLevel: LogLevel, lazyMessage: () -> String) {
        when (logLevel) {
            LogLevel.TRACE -> NSLog("[$tag] TRACE: ${lazyMessage()}")
            LogLevel.DEBUG -> NSLog("[$tag] DEBUG: ${lazyMessage()}")
            LogLevel.INFO -> NSLog("[$tag] INFO: ${lazyMessage()}")
            LogLevel.WARN -> NSLog("[$tag] WARN: ${lazyMessage()}")
            LogLevel.ERROR -> NSLog("[$tag] ERROR: ${lazyMessage()}")
        }
    }

    override fun log(tag: String, logLevel: LogLevel, throwable: Throwable) {
        when (logLevel) {
            LogLevel.TRACE -> NSLog("[$tag] TRACE: ${throwable.message ?: "No message"}")
            LogLevel.DEBUG -> NSLog("[$tag] DEBUG: ${throwable.message ?: "No message"}")
            LogLevel.INFO -> NSLog("[$tag] INFO: ${throwable.message ?: "No message"}")
            LogLevel.WARN -> NSLog("[$tag] WARN: ${throwable.message ?: "No message"}")
            LogLevel.ERROR -> NSLog("[$tag] ERROR: ${throwable.message ?: "No message"}")
        }
    }
}