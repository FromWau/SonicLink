package at.tfro.sonic_link.core.logger

import at.tfro.sonic_link.core.GB
import java.io.File


actual class PlatformLoggerFactory(
    val dataDir: File = getAppDataDir(),
    val logStdout: Boolean = true,
) {
    actual fun create(): PlatformLogger {
        return object : PlatformLogger {
            private val logFile = File(dataDir, "sonic_link.log")
            private val maxSize = 2.GB

            init {
                logFile.createNewFileIfNotExists()
            }

            private val lock = Any()
            private fun writeLogToFile(message: String) {
                synchronized(lock) {
                    if (logFile.length() > maxSize) {
                        logFile.delete()
                        logFile.createNewFileIfNotExists()
                    }

                    logFile.appendText("$message\n")
                }
            }

            override fun log(tag: String, logLevel: LogLevel, lazyMessage: () -> String) {
                this.toLogString(tag, logLevel, lazyMessage).let { message ->
                    writeLogToFile(message)
                    if (logStdout) {
                        println(message)
                    }
                }
            }

            override fun log(tag: String, logLevel: LogLevel, throwable: Throwable) {
                this.toLogString(tag, logLevel, throwable).let { message ->
                    writeLogToFile(message)
                    if (logStdout) {
                        println(message)
                    }
                }
            }
        }
    }
}
