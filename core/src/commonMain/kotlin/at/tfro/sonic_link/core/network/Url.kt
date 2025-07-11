package at.tfro.sonic_link.core.network

fun String.ensureProtocol(): String = when {
    this.startsWith("https://") -> this
    this.startsWith("http://") -> this
    else ->  "https://$this"
}