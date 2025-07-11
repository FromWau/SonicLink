package at.tfro.sonic_link.core.logger

import android.util.Log

actual class PlatformLoggerFactory {
    actual fun create(): PlatformLogger {
        return object : PlatformLogger {
            override fun log(tag: String, logLevel: LogLevel, lazyMessage: () -> String) {
                when (logLevel) {
                    LogLevel.TRACE -> Log.v(tag, lazyMessage())
                    LogLevel.DEBUG -> Log.d(tag, lazyMessage())
                    LogLevel.INFO -> Log.i(tag, lazyMessage())
                    LogLevel.WARN -> Log.w(tag, lazyMessage())
                    LogLevel.ERROR -> Log.e(tag, lazyMessage())
                }
            }

            override fun log(tag: String, logLevel: LogLevel, throwable: Throwable) {
                when (logLevel) {
                    LogLevel.TRACE -> Log.v(tag, throwable.message, throwable)
                    LogLevel.DEBUG -> Log.d(tag, throwable.message, throwable)
                    LogLevel.INFO -> Log.i(tag, throwable.message, throwable)
                    LogLevel.WARN -> Log.w(tag, throwable.message, throwable)
                    LogLevel.ERROR -> Log.e(tag, throwable.message, throwable)
                }
            }
        }
    }
}