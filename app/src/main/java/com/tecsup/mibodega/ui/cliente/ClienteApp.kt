package com.tecsup.mibodega.ui.cliente

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.favoritos.FavoritosScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.pedidos.MisPedidosScreen
import com.tecsup.mibodega.ui.cliente.screens.pedidos.PedidoHistorial
import com.tecsup.mibodega.ui.cliente.screens.perfil.PerfilScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen
import com.tecsup.mibodega.ui.theme.BodegaTheme

object Rutas {
    const val BIENVENIDA = "bienvenida"
    const val REGISTRO = "registro"
    const val INICIO = "inicio"
    const val PERFIL = "perfil"
    const val CARRITO = "carrito"
    const val DATOS_ENTREGA = "datos_entrega"
    const val CONFIRMACION = "confirmacion"
    const val PEDIDOS = "pedidos"
    const val FAVORITOS = "favoritos"
    const val DETALLE_PRODUCTO = "detalle_producto/{productoId}"
}

@Composable
fun ClienteApp() {
    var esModoOscuro by rememberSaveable { mutableStateOf(false) }
    var nombreUsuario by remember { mutableStateOf("Usuario Registrado") }
    var clienteDireccion by remember { mutableStateOf("") }
    var clienteReferencia by remember { mutableStateOf("") }

    val navController = rememberNavController()

    var productos by remember {
        mutableStateOf(
            listOf(
                Producto(
                    id = 1,
                    nombre = "Arroz Costeño",
                    categoria = "Abarrotes",
                    precio = 4.50,
                    peso = "1 kg",
                    imagenUrl = "https://via.placeholder.com/150",
                    esFavorito = false
                ),
                Producto(
                    id = 2,
                    nombre = "Aceite Primor",
                    categoria = "Abarrotes",
                    precio = 8.90,
                    peso = "1 L",
                    imagenUrl = "https://via.placeholder.com/150",
                    esFavorito = false
                ),
                Producto(
                    id = 3,
                    nombre = "Leche Gloria",
                    categoria = "Bebidas",
                    precio = 5.20,
                    peso = "1 L",
                    imagenUrl = "https://via.placeholder.com/150",
                    esFavorito = false
                ),
                Producto(
                    id = 4,
                    nombre = "Galleta Oreo",
                    categoria = "Snacks",
                    precio = 3.50,
                    peso = "126 g",
                    imagenUrl = "https://via.placeholder.com/150",
                    esFavorito = false
                )
            )
        )
    }

    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }
    var historialPedidos by remember { mutableStateOf<List<PedidoHistorial>>(emptyList()) }
    var ultimoTotalPedido by remember { mutableStateOf(0.0) }

    val cantidadCarritoTotal = carrito.sumOf { it.cantidad }

    BodegaTheme(darkTheme = esModoOscuro) {
        NavHost(
            navController = navController,
            startDestination = Rutas.BIENVENIDA // <-- ¡AQUÍ ESTÁ EL CAMBIO! Ahora arranca en Bienvenida
        ) {
            // Screen: Bienvenida / Login
            composable(Rutas.BIENVENIDA) {
                BienvenidaScreen(
                    onIniciarSesion = {
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                        }
                    },
                    onRegistrarse = {
                        navController.navigate(Rutas.REGISTRO)
                    },
                    onTerminos = { }
                )
            }

            // Screen: Registro
            composable(Rutas.REGISTRO) {
                RegistroScreen(
                    onRegistroExitoso = {
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                        }
                    },
                    onVolver = { navController.popBackStack() }
                )
            }

            // Screen: Inicio
            composable(Rutas.INICIO) {
                InicioScreen(
                    productos = productos,
                    cantidadCarrito = cantidadCarritoTotal,
                    onVerCarrito = { navController.navigate(Rutas.CARRITO) },
                    onVerPedidos = { navController.navigate(Rutas.PEDIDOS) },
                    onVerPerfil = { navController.navigate(Rutas.PERFIL) },
                    onProductoClick = { prod ->
                        navController.navigate("detalle_producto/${prod.id}")
                    },
                    onAgregarProducto = { prod ->
                        val existe = carrito.find { it.producto.id == prod.id }
                        carrito = if (existe != null) {
                            carrito.map {
                                if (it.producto.id == prod.id) it.copy(cantidad = it.cantidad + 1)
                                else it
                            }
                        } else {
                            carrito + ItemCarrito(producto = prod, cantidad = 1)
                        }
                    },
                    onToggleFavorito = { prod ->
                        productos = productos.map {
                            if (it.id == prod.id) it.copy(esFavorito = !it.esFavorito)
                            else it
                        }
                    }
                )
            }

            // Screen: Perfil
            composable(Rutas.PERFIL) {
                PerfilScreen(
                    nombreUsuario = nombreUsuario,
                    esModoOscuro = esModoOscuro,
                    onCambiarModoOscuro = { esModoOscuro = it },
                    onVerPedidos = { navController.navigate(Rutas.PEDIDOS) },
                    onVerFavoritos = { navController.navigate(Rutas.FAVORITOS) },
                    onVolver = { navController.popBackStack() }
                )
            }

            // Screen: Carrito de Compras
            composable(Rutas.CARRITO) {
                CarritoScreen(
                    itemsCarrito = carrito,
                    onIncrementar = { item ->
                        carrito = carrito.map {
                            if (it.producto.id == item.producto.id) it.copy(cantidad = it.cantidad + 1)
                            else it
                        }
                    },
                    onDecrementar = { item ->
                        carrito = carrito.mapNotNull {
                            if (it.producto.id == item.producto.id) {
                                if (it.cantidad > 1) it.copy(cantidad = it.cantidad - 1) else null
                            } else it
                        }
                    },
                    onEliminar = { item ->
                        carrito = carrito.filter { it.producto.id != item.producto.id }
                    },
                    onVolver = { navController.popBackStack() },
                    onContinuarPedido = { navController.navigate(Rutas.DATOS_ENTREGA) }
                )
            }

            // Screen: Datos de Entrega y Pago
            composable(Rutas.DATOS_ENTREGA) {
                val subtotal = carrito.sumOf { it.producto.precio * it.cantidad }
                DatosEntregaScreen(
                    subtotal = subtotal,
                    direccionInicial = clienteDireccion,
                    referenciaInicial = clienteReferencia,
                    onVolver = { navController.popBackStack() },
                    onConfirmarPedido = { metodoEntrega, metodoPago, costoTotal ->
                        ultimoTotalPedido = costoTotal
                        val nuevoPedido = PedidoHistorial(
                            id = "#10${historialPedidos.size + 25}",
                            fecha = "Hoy",
                            total = costoTotal,
                            estado = "En preparación",
                            metodoEntrega = "$metodoEntrega - Pago con $metodoPago"
                        )
                        historialPedidos = listOf(nuevoPedido) + historialPedidos
                        carrito = emptyList()
                        navController.navigate(Rutas.CONFIRMACION) {
                            popUpTo(Rutas.INICIO) { inclusive = false }
                        }
                    }
                )
            }

            // Screen: Confirmación del Pedido
            composable(Rutas.CONFIRMACION) {
                ConfirmacionScreen(
                    total = ultimoTotalPedido,
                    onVolverInicio = {
                        navController.popBackStack(Rutas.INICIO, false)
                    }
                )
            }

            // Screen: Historial de Pedidos
            composable(Rutas.PEDIDOS) {
                MisPedidosScreen(
                    pedidosHistorial = historialPedidos,
                    onVolver = { navController.popBackStack() }
                )
            }

            // Screen: Mis Productos Favoritos
            composable(Rutas.FAVORITOS) {
                FavoritosScreen(
                    productos = productos.filter { it.esFavorito },
                    onProductoClick = { prod -> navController.navigate("detalle_producto/${prod.id}") },
                    onAgregarProducto = { prod ->
                        val existe = carrito.find { it.producto.id == prod.id }
                        carrito = if (existe != null) {
                            carrito.map {
                                if (it.producto.id == prod.id) it.copy(cantidad = it.cantidad + 1)
                                else it
                            }
                        } else {
                            carrito + ItemCarrito(producto = prod, cantidad = 1)
                        }
                    },
                    onToggleFavorito = { prod ->
                        productos = productos.map {
                            if (it.id == prod.id) it.copy(esFavorito = !it.esFavorito)
                            else it
                        }
                    },
                    onVolver = { navController.popBackStack() }
                )
            }

            // Screen: Detalle del Producto
            composable(
                route = Rutas.DETALLE_PRODUCTO,
                arguments = listOf(navArgument("productoId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productoIdString = backStackEntry.arguments?.getString("productoId") ?: ""
                val productoIdInt = productoIdString.toIntOrNull()
                val producto = productos.find { it.id == productoIdInt }
                if (producto != null) {
                    DetalleProductoScreen(
                        producto = producto,
                        onVolver = { navController.popBackStack() },
                        onAgregarProducto = { prod ->
                            val existe = carrito.find { it.producto.id == prod.id }
                            carrito = if (existe != null) {
                                carrito.map {
                                    if (it.producto.id == prod.id) it.copy(cantidad = it.cantidad + 1)
                                    else it
                                }
                            } else {
                                carrito + ItemCarrito(producto = prod, cantidad = 1)
                            }
                        },
                        onToggleFavorito = { prod ->
                            productos = productos.map {
                                if (it.id == prod.id) it.copy(esFavorito = !it.esFavorito)
                                else it
                            }
                        }
                    )
                }
            }
        }
    }
}