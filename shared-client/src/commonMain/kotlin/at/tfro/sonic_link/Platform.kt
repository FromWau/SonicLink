package at.tfro.sonic_link

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform