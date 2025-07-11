package at.tfro.sonic_link.compose_app

import android.app.Application
import at.tfro.sonic_link.shared_client.di.initKoin
import org.koin.android.ext.koin.androidContext

class SonicLinkApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@SonicLinkApplication)
        }
    }
}