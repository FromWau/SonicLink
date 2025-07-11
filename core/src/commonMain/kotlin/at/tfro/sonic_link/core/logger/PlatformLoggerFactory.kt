package at.tfro.sonic_link.core.logger

expect class PlatformLoggerFactory {
    fun create(): PlatformLogger
}
