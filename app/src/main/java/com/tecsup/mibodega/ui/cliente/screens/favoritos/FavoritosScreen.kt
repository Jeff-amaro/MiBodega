package com.tecsup.mibodega.ui.cliente.screens.favoritos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.cliente.modelo.Producto

@Composable
fun FavoritosScreen(
    productos: List<Producto>,
    onProductoClick: (Producto) -> Unit,
    onAgregarProducto: (Producto) -> Unit,
    onToggleFavorito: (Producto) -> Unit,
    onVolver: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onVolver) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Mis Favoritos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (productos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tienes productos favoritos aún.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(productos) { prod ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onProductoClick(prod) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(prod.nombre, fontWeight = FontWeight.Bold)
                                    Text("S/ ${String.format("%.2f", prod.precio)}")
                                }
                                Row {
                                    Button(onClick = { onAgregarProducto(prod) }) {
                                        Text("Agregar")
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    IconButton(onClick = { onToggleFavorito(prod) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Quitar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}