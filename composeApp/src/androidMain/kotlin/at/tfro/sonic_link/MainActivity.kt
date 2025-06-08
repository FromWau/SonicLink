package at.tfro.sonic_link

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import at.tfro.sonic_link.app.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}
