package com.tecsup.mibodega.ui.cliente.screens.carrito

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito

@Composable
fun CarritoScreen(
    itemsCarrito: List<ItemCarrito>,
    onIncrementar: (ItemCarrito) -> Unit,
    onDecrementar: (ItemCarrito) -> Unit,
    onEliminar: (ItemCarrito) -> Unit,
    onVaciarCarrito: () -> Unit = {},
    onVolver: () -> Unit,
    onContinuarPedido: () -> Unit
) {
    var itemAEliminar by remember { mutableStateOf<ItemCarrito?>(null) }
    val subtotal = itemsCarrito.sumOf { it.producto.precio * it.cantidad }

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onVolver) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.Black)
                    }
                    Text(text = "Mi carrito", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                if (itemsCarrito.isNotEmpty()) {
                    IconButton(onClick = onVaciarCarrito) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Vaciar carrito", tint = Color.Black, modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Requisito 4: Mensaje de carrito vacío
            if (itemsCarrito.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tu carrito está vacío 🛒", fontSize = 16.sp, color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(itemsCarrito) { item ->
                        ItemCarritoRow(
                            item = item,
                            onIncrementar = { onIncrementar(item) },
                            onDecrementar = { onDecrementar(item) },
                            onEliminar = { itemAEliminar = item }
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFEEEEEE))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Subtotal", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text(text = "S/ ${String.format("%.2f", subtotal)}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onContinuarPedido,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text(text = "Continuar pedido", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Requisito 5: AlertDialog de confirmación de eliminación
        itemAEliminar?.let { item ->
            AlertDialog(
                onDismissRequest = { itemAEliminar = null },
                title = { Text("Confirmar eliminación") },
                text = { Text("¿Deseas eliminar '${item.producto.nombre}' del carrito?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onEliminar(item)
                            itemAEliminar = null
                        }
                    ) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { itemAEliminar = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
private fun ItemCarritoRow(
    item: ItemCarrito,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(70.dp).clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(model = item.producto.imagenUrl, contentDescription = item.producto.nombre, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Fit)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = "${item.producto.nombre} ${item.producto.peso}", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text(text = "S/ ${String.format("%.2f", item.producto.precio)}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE53935))

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(Color(0xFFF2F4F7), RoundedCornerShape(16.dp)).padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                IconButton(onClick = onDecrementar, modifier = Modifier.size(28.dp).background(Color.White, CircleShape)) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Restar", tint = Color.Black, modifier = Modifier.size(16.dp))
                }
                Text(text = item.cantidad.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 14.dp))
                IconButton(onClick = onIncrementar, modifier = Modifier.size(28.dp).background(Color(0xFF2E7D32), CircleShape)) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Sumar", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }

        IconButton(onClick = onEliminar) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Gray, modifier = Modifier.size(20.dp))
        }
    }
}