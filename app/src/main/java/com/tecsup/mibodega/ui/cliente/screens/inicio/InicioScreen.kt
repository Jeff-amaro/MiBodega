package com.tecsup.mibodega.ui.cliente.screens.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.mibodega.ui.cliente.modelo.Producto

enum class OrdenPrecio { NINGUNO, MENOR_MAYOR, MAYOR_MENOR }

@Composable
fun InicioScreen(
    productos: List<Producto>,
    cantidadCarrito: Int,
    onVerCarrito: () -> Unit,
    onVerPedidos: () -> Unit,
    onVerPerfil: () -> Unit,
    onProductoClick: (Producto) -> Unit,
    onAgregarProducto: (Producto) -> Unit,
    onToggleFavorito: (Producto) -> Unit
) {
    var busqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    var ordenPrecio by remember { mutableStateOf(OrdenPrecio.NINGUNO) }

    val categorias = listOf("Todos", "Bebidas", "Abarrotes", "Snacks")

    val productosFiltrados = remember(busqueda, categoriaSeleccionada, ordenPrecio, productos) {
        var lista = productos.filter { prod ->
            val coincideCat = categoriaSeleccionada == "Todos" || prod.categoria.equals(categoriaSeleccionada, ignoreCase = true)
            val coincideBusqueda = prod.nombre.contains(busqueda, ignoreCase = true)
            coincideCat && coincideBusqueda
        }

        when (ordenPrecio) {
            OrdenPrecio.MENOR_MAYOR -> lista = lista.sortedBy { it.precio }
            OrdenPrecio.MAYOR_MENOR -> lista = lista.sortedByDescending { it.precio }
            OrdenPrecio.NINGUNO -> {}
        }
        lista
    }

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onVerPedidos,
                    icon = { Icon(Icons.Outlined.ReceiptLong, contentDescription = "Pedidos") },
                    label = { Text("Pedidos") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onVerPerfil,
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                            append("Mi ")
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                            append("Bodega")
                        }
                    }
                )

                IconButton(onClick = onVerCarrito) {
                    BadgedBox(
                        badge = {
                            if (cantidadCarrito > 0) {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.error,
                                    contentColor = MaterialTheme.colorScheme.onError
                                ) {
                                    Text(text = cantidadCarrito.toString())
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar productos...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Chips de Categorías
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categorias) { cat ->
                    val seleccionada = cat == categoriaSeleccionada
                    FilterChip(
                        selected = seleccionada,
                        onClick = { categoriaSeleccionada = cat },
                        label = { Text(cat, fontWeight = if (seleccionada) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Chips de Ordenamiento por Precio
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Precio:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
                FilterChip(
                    selected = ordenPrecio == OrdenPrecio.MENOR_MAYOR,
                    onClick = {
                        ordenPrecio = if (ordenPrecio == OrdenPrecio.MENOR_MAYOR) OrdenPrecio.NINGUNO else OrdenPrecio.MENOR_MAYOR
                    },
                    label = { Text("Menor a mayor ⬆️", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        labelColor = MaterialTheme.colorScheme.onSurface
                    )
                )
                FilterChip(
                    selected = ordenPrecio == OrdenPrecio.MAYOR_MENOR,
                    onClick = {
                        ordenPrecio = if (ordenPrecio == OrdenPrecio.MAYOR_MENOR) OrdenPrecio.NINGUNO else OrdenPrecio.MAYOR_MENOR
                    },
                    label = { Text("Mayor a menor ⬇️", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        labelColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Productos destacados",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(productosFiltrados) { producto ->
                    TarjetaProducto(
                        producto = producto,
                        onClick = { onProductoClick(producto) },
                        onAgregar = { onAgregarProducto(producto) },
                        onToggleFavorito = { onToggleFavorito(producto) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaProducto(
    producto: Producto,
    onClick: () -> Unit,
    onAgregar: () -> Unit,
    onToggleFavorito: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = producto.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                IconButton(
                    onClick = onToggleFavorito,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (producto.esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (producto.esFavorito) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = producto.nombre,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )

            Text(
                text = producto.peso,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "S/ ${String.format("%.2f", producto.precio)}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .clickable { onAgregar() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}