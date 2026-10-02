package com.tecsup.mibodega.ui.cliente.screens.perfil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PerfilScreen(
    nombreUsuario: String = "Cliente Mi Bodega",
    esModoOscuro: Boolean,
    onCambiarModoOscuro: (Boolean) -> Unit,
    onVerPedidos: () -> Unit,
    onVerFavoritos: () -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text("Mi Perfil", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))

        // Card de usuario
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Person, contentDescription = null)
                Spacer(Modifier.padding(8.dp))
                Column {
                    Text(text = if (nombreUsuario.isBlank()) "Cliente Mi Bodega" else nombreUsuario, fontWeight = FontWeight.Bold)
                    Text("Usuario Activo", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // Opción: Modo Oscuro
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.DarkMode, contentDescription = null)
            Spacer(Modifier.padding(8.dp))
            Text("Modo Oscuro", modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
            Switch(
                checked = esModoOscuro,
                onCheckedChange = onCambiarModoOscuro
            )
        }

        Spacer(Modifier.height(16.dp))

        // Opción: Ver Pedidos
        TextButton(
            onClick = onVerPedidos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Receipt, contentDescription = null)
                Spacer(Modifier.padding(8.dp))
                Text("Historial de Pedidos", fontWeight = FontWeight.Medium)
            }
        }

        // Opción: Ver Favoritos
        TextButton(
            onClick = onVerFavoritos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Favorite, contentDescription = null)
                Spacer(Modifier.padding(8.dp))
                Text("Mis Productos Favoritos", fontWeight = FontWeight.Medium)
            }
        }
    }
}