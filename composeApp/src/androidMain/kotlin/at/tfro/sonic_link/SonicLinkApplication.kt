package at.tfro.sonic_link

import android.app.Application
import at.tfro.sonic_link.di.initKoin
import org.koin.android.ext.koin.androidContext

class SonicLinkApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@SonicLinkApplication)
        }
    }
}