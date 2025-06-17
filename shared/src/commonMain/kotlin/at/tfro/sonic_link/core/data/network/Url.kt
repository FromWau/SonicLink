package at.tfro.sonic_link.core.data.network

fun String.ensureHttps(): String = if (this.startsWith("https://")) this else { "https://$this" }