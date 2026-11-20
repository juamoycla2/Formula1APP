package cat.exemple.pilotosf1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cat.exemple.pilotosf1.ui.PilotosApp
import cat.exemple.pilotosf1.ui.theme.PilotosF1Theme
import cat.exemple.pilotosf1.model.Piloto
import cat.exemple.pilotosf1.ui.DetallScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PilotosF1Theme {
                PilotosApp()
            }
        }
    }
}