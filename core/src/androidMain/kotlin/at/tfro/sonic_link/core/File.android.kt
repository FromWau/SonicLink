package at.tfro.sonic_link.core

import android.content.Context

actual class FileFactory(private val context: Context) {
    actual fun appDir(): String = context.dataDir.absolutePath
}