package at.tfro.sonic_link

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object Log {
    fun tag(tag: String): TaggedLog = TaggedLog(LoggerFactory.getLogger(tag))

    fun t(msg: () -> String) = getCallerLogger().let { log ->
        if (log.isTraceEnabled) log.trace(msg())
    }

    fun d(msg: () -> String) = getCallerLogger().let { log ->
        if (log.isDebugEnabled) log.debug(msg())
    }

    fun i(msg: () -> String) = getCallerLogger().let { log ->
        if (log.isInfoEnabled) log.info(msg())
    }

    fun w(msg: () -> String) = getCallerLogger().let { log ->
        if (log.isWarnEnabled) log.warn(msg())
    }

    fun e(msg: () -> String) = getCallerLogger().let { log ->
        if (log.isErrorEnabled) log.error(msg())
    }

    fun e(throwable: Throwable) = getCallerLogger().let { log ->
        if (log.isErrorEnabled) log.error(throwable.message, throwable)
    }

    private fun getCallerLogger(): Logger {
        val stackTrace = Thread.currentThread().stackTrace
        val element = stackTrace.firstOrNull {
            it.className != Log::class.java.name &&
                    !it.className.startsWith("java.lang.Thread")
        } ?: stackTrace.last()

        // Replace $ with . and remove package prefix
        var simpleName = element.className.substringAfterLast('.').replace('$', '.')

        // Remove trailing .<digits> if present
        simpleName = simpleName.replace(Regex("\\.\\d+$"), "")

        return LoggerFactory.getLogger(simpleName)
    }


    class TaggedLog(private val log: Logger) {
        fun t(msg: () -> String) {
            if (log.isTraceEnabled) log.trace(msg())
        }

        fun d(msg: () -> String) {
            if (log.isDebugEnabled) log.debug(msg())
        }

        fun i(msg: () -> String) {
            if (log.isInfoEnabled) log.info(msg())
        }

        fun w(msg: () -> String) {
            if (log.isWarnEnabled) log.warn(msg())
        }

        fun e(msg: () -> String) {
            if (log.isErrorEnabled) log.error(msg())
        }

        fun e(throwable: Throwable) {
            if (log.isErrorEnabled) log.error(throwable.message, throwable)
        }
    }
}

