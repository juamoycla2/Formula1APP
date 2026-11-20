package cat.exemple.pilotosf1.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

// Decideix que pantalla es veu i aplica el tema
@Composable
fun PilotosApp() {
    val viewModel: PilotosViewModel = viewModel()

    LaunchedEffect(viewModel) {
        viewModel.iniciar()
    }

    val modeFosc = when (viewModel.tema) {
        "CLAR" -> false
        "FOSC" -> true
        else -> isSystemInDarkTheme()
    }

    MaterialTheme(
        colorScheme = if (modeFosc) darkColorScheme() else lightColorScheme()
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {

            val pilotoSeleccionat = viewModel.pilotos.firstOrNull { it.numero == viewModel.numeroDetall }

            if (viewModel.configuracioOberta) {
                ConfiguracioScreen(viewModel = viewModel)
            } else if (pilotoSeleccionat == null) {
                PilotosScreen(viewModel = viewModel)
            } else {
                DetallScreen(piloto = pilotoSeleccionat, viewModel = viewModel)
            }
        }
    }
}