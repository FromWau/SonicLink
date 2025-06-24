package at.tfro.sonic_link.logger

expect class PlatformLoggerFactory {
    fun create(): PlatformLogger
}
