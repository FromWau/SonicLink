package at.tfro.sonic_link.shared_client

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform