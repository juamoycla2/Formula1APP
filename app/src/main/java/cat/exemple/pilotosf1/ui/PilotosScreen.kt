package cat.exemple.pilotosf1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import cat.exemple.pilotosf1.R

// Llista amb carga error i botons
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PilotosScreen(
    viewModel: PilotosViewModel,
    modifier: Modifier = Modifier
) {
    val pilotos = if (viewModel.nomesFavorits) {
        viewModel.pilotos.filter { it.esFavorito }
    } else {
        viewModel.pilotos
    }
    val isLoading = viewModel.carregant
    val missatgeError = viewModel.missatgeError

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Pilotos F1", fontWeight = FontWeight.Bold, color = Color.White) },
                actions = {
                    IconButton(
                        onClick = { viewModel.carregarPilotos() },
                        enabled = !isLoading
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_refresh),
                            contentDescription = "Actualitzar pilotos"
                        )
                    }
                    IconButton(onClick = { viewModel.canviarFiltre() }) {
                        Icon(
                            painter = painterResource(
                                if (viewModel.nomesFavorits) R.drawable.ic_favorite
                                else R.drawable.ic_favorite_border
                            ),
                            contentDescription = "Filtrar favorits"
                        )
                    }
                    IconButton(onClick = { viewModel.obrirConfiguracio() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_settings),
                            contentDescription = "Configuració"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE10600),
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        when {
            isLoading && viewModel.pilotos.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            missatgeError != null && viewModel.pilotos.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(missatgeError)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.carregarPilotos() }) {
                        Text("Reintentar")
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = pilotos,
                        key = { it.numero }
                    ) { piloto ->
                        PilotoItem(
                            piloto = piloto,
                            onClick = { viewModel.obrirDetall(piloto.numero) },
                            onToggleFavorit = { viewModel.canviarFavorit(piloto.numero) }
                        )
                    }
                }
            }
        }
    }
}