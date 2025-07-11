@file:Suppress("unused")

package at.tfro.sonic_link.core.logger

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

// TODO: Find a way to use this in Compose context maybe with LocalContext?
interface Logger {
    fun log(tag: String, logLevel: LogLevel, lazyMessage: () -> String)
    fun log(tag: String, logLevel: LogLevel, throwable: Throwable)
}

fun Logger.tag(tag: String): TaggedLogger = TaggedLogger(tag, this)
fun Logger.toLogString(tag: String, logLevel: LogLevel, lazyMessage: () -> String): String {
    val timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val formattedDate = formatDateTime(timestamp)
    val level = logLevel.name.padEnd(5) // pad to 5 characters

    val logMsg = "$formattedDate $level ${tag.take(35)} - ${lazyMessage()}"
    return logMsg
}

fun Logger.toLogString(tag: String, logLevel: LogLevel, throwable: Throwable): String {
    val timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val formattedDate = formatDateTime(timestamp)
    val level = logLevel.name.padEnd(5) // pad to 5 characters

    val logMsg = "$formattedDate $level ${tag.take(35)} - ${throwable.stackTraceToString()}"
    return logMsg
}

private fun formatDateTime(dt: LocalDateTime): String {
    val year = dt.year.toString().padStart(4, '0')
    val month = dt.monthNumber.toString().padStart(2, '0')
    val day = dt.dayOfMonth.toString().padStart(2, '0')
    val hour = dt.hour.toString().padStart(2, '0')
    val minute = dt.minute.toString().padStart(2, '0')
    val second = dt.second.toString().padStart(2, '0')
    val millisecond = (dt.nanosecond / 1_000_000).toString().padStart(3, '0')

    return "$year-$month-$day $hour:$minute:$second.$millisecond"
}


fun TaggedLogger.t(msg: () -> String) = this.log(LogLevel.TRACE, msg)
fun TaggedLogger.d(msg: () -> String) = this.log(LogLevel.DEBUG, msg)
fun TaggedLogger.i(msg: () -> String) = this.log(LogLevel.INFO, msg)
fun TaggedLogger.w(msg: () -> String) = this.log(LogLevel.WARN, msg)
fun TaggedLogger.e(msg: () -> String) = this.log(LogLevel.ERROR, msg)
fun TaggedLogger.e(throwable: Throwable) = this.log(LogLevel.ERROR, throwable)

enum class LogLevel {
    TRACE, DEBUG, INFO, WARN, ERROR
}

class TaggedLogger(
    private val tag: String,
    private val delegate: Logger,
) {
    fun log(logLevel: LogLevel, lazyMessage: () -> String) =
        delegate.log(tag, logLevel, lazyMessage)

    fun log(logLevel: LogLevel, throwable: Throwable) =
        delegate.log(tag, logLevel, throwable)
}