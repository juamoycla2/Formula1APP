package cat.exemple.pilotosf1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cat.exemple.pilotosf1.R
import cat.exemple.pilotosf1.model.Piloto
import coil.compose.AsyncImage
import androidx.compose.foundation.clickable

// Una tajeta de la llista
@Composable
fun PilotoItem(
    piloto: Piloto,
    onClick: () -> Unit,
    onToggleFavorit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto del piloto redona
            AsyncImage(
                model = piloto.foto,
                contentDescription = "Foto de ${piloto.nom}",
                placeholder = painterResource(R.drawable.ic_person),
                error = painterResource(R.drawable.ic_error_outline),
                modifier = Modifier.size(64.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))

            // Dades del piloto
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = piloto.nom,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "#${piloto.numero} • ${piloto.acronim}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = piloto.equip,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Coret
            IconButton(onClick = onToggleFavorit) {
                Icon(
                    painter = painterResource(
                        if (piloto.esFavorito) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                    ),
                    contentDescription = "Canviar favorit",
                    tint = Color(0xFFC62828)
                )
            }
        }
    }
}