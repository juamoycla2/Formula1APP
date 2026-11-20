package cat.exemple.pilotosf1.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cat.exemple.pilotosf1.R

// Elecció del tema
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracioScreen(
    viewModel: PilotosViewModel,
    modifier: Modifier = Modifier
) {
    val opcions = listOf(
        "SISTEMA" to "Seguir el sistema",
        "CLAR" to "Clar",
        "FOSC" to "Fosc"
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Configuració") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.tancarConfiguracio() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Tornar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("Tema", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            for ((clau, text) in opcions) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.canviarTema(clau) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = viewModel.tema == clau,
                        onClick = { viewModel.canviarTema(clau) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text)
                }
            }
        }
    }
}