package at.tfro.sonic_link.logger

import at.tfro.sonic_link.GB
import java.io.File

actual class PlatformLogger actual constructor() : Logger {
    private val logFile = File(getAppDataDir(), "sonic_link.log")
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
        writeLogToFile(this.toLogString(tag, logLevel, lazyMessage))
    }

    override fun log(tag: String, logLevel: LogLevel, throwable: Throwable) {
        writeLogToFile(this.toLogString(tag, logLevel, throwable))
    }
}
