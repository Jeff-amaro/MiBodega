package com.tecsup.mibodega.ui.cliente.screens.pedidos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class PedidoHistorial(
    val id: String,
    val fecha: String,
    val total: Double,
    val estado: String,
    val metodoEntrega: String
)

@Composable
fun MisPedidosScreen(
    pedidosHistorial: List<PedidoHistorial>,
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
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
            Text("Mis Pedidos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))

        if (pedidosHistorial.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aún no has realizado ningún pedido.")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(pedidosHistorial) { pedido ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.ReceiptLong, contentDescription = null)
                            Spacer(Modifier.padding(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Pedido ${pedido.id}", fontWeight = FontWeight.Bold)
                                Text("${pedido.fecha} - ${pedido.metodoEntrega}", style = MaterialTheme.typography.bodySmall)
                                Text(
                                    "Estado: ${pedido.estado}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                "S/ %.2f".format(pedido.total),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}