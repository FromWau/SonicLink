package at.fro.sonic_link

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform