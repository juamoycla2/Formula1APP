package cat.exemple.pilotosf1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cat.exemple.pilotosf1.R
import cat.exemple.pilotosf1.model.Piloto
import coil.compose.AsyncImage

// La ficha de un piloto
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallScreen(
    piloto: Piloto?,
    viewModel: PilotosViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(piloto?.nom ?: "Detall") },

                navigationIcon = {
                    IconButton(onClick = { viewModel.tancarDetall() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Tornar a la llista"
                        )
                    }
                },

                actions = {
                    if (piloto != null) {
                        IconButton(onClick = { viewModel.canviarFavorit(piloto.numero) }) {
                            Icon(
                                painter = painterResource(
                                    if (piloto.esFavorito) R.drawable.ic_favorite
                                    else R.drawable.ic_favorite_border
                                ),
                                contentDescription = "Canviar favorit",
                                tint = if (piloto.esFavorito) Color(0xFFC62828)
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->

        if (piloto == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No s'ha trobat el piloto sol·licitat.")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Foto gran
                AsyncImage(
                    model = piloto.foto,
                    contentDescription = "Foto de ${piloto.nom}",
                    placeholder = painterResource(R.drawable.ic_person),
                    error = painterResource(R.drawable.ic_error_outline),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.2f)
                        .background(Color.DarkGray),
                    contentScale = ContentScale.Fit
                )

                // Textos
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = piloto.nom,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    if (piloto.acronim.isNotBlank()) {
                        Text(
                            text = piloto.acronim,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "#${piloto.numero}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                        Surface(
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = piloto.equip,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}