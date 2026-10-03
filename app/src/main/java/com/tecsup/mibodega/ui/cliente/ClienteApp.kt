package com.tecsup.mibodega.ui.cliente

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.favoritos.FavoritosScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.pedidos.MisPedidosScreen
import com.tecsup.mibodega.ui.cliente.screens.perfil.PerfilScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen
import com.tecsup.mibodega.ui.theme.BodegaTheme

private object Rutas {
    const val BIENVENIDA = "bienvenida"
    const val REGISTRO = "registro"
    const val INICIO = "inicio"
    const val DETALLE = "detalle/{productoId}"
    const val CARRITO = "carrito"
    const val DATOS_ENTREGA = "datos_entrega"
    const val CONFIRMACION = "confirmacion"
    const val MIS_PEDIDOS = "mis_pedidos"
    const val FAVORITOS = "favoritos"
    const val PERFIL = "perfil"

    fun detalle(productoId: Int) = "detalle/$productoId"
}

@Composable
fun ClienteApp() {
    val navController = rememberNavController()

    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }
    var esModoOscuro by remember { mutableStateOf(false) }
    var mostrarTerminos by remember { mutableStateOf(false) }

    var esNuevoRegistro by remember { mutableStateOf(true) }

    var clienteNombre by remember { mutableStateOf("") }
    var clienteTelefono by remember { mutableStateOf("") }
    var clienteDireccion by remember { mutableStateOf("") }
    var clienteReferencia by remember { mutableStateOf("") }

    BodegaTheme {
        NavHost(
            navController = navController,
            startDestination = Rutas.BIENVENIDA,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() }
        ) {
            composable(Rutas.BIENVENIDA) {
                BienvenidaScreen(
                    onRegistrarse = {
                        esNuevoRegistro = true
                        navController.navigate(Rutas.REGISTRO)
                    },
                    onIniciarSesion = {
                        esNuevoRegistro = false
                        navController.navigate(Rutas.REGISTRO)
                    },
                    onTerminos = { mostrarTerminos = true }
                )
            }

            composable(Rutas.REGISTRO) {
                RegistroScreen(
                    esNuevoRegistro = esNuevoRegistro,
                    onVolver = { navController.popBackStack() },
                    onConfirmar = { nombre, telefono, direccion, referencia ->
                        if (nombre.isNotEmpty()) clienteNombre = nombre
                        if (telefono.isNotEmpty()) clienteTelefono = telefono
                        if (direccion.isNotEmpty()) clienteDireccion = direccion
                        if (referencia.isNotEmpty()) clienteReferencia = referencia

                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                        }
                    }
                )
            }

            composable(Rutas.INICIO) {
                InicioScreen(
                    cantidadCarrito = carrito.sumOf { it.cantidad },
                    onVerCarrito = { navController.navigate(Rutas.CARRITO) },
                    onProductoClick = { producto ->
                        navController.navigate(Rutas.detalle(producto.id))
                    },
                    onAgregarProducto = { producto ->
                        carrito = agregarOSumarProducto(carrito, producto, 1)
                    }
                )
            }

            composable(
                route = Rutas.DETALLE,
                arguments = listOf(navArgument("productoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
                val producto = listaProductosFake.first { it.id == productoId }

                DetalleProductoScreen(
                    producto = producto,
                    onVolver = { navController.popBackStack() },
                    onAgregarAlCarrito = { productoSeleccionado, cantidad ->
                        carrito = agregarOSumarProducto(carrito, productoSeleccionado, cantidad)
                        navController.popBackStack()
                    }
                )
            }

            composable(Rutas.CARRITO) {
                CarritoScreen(
                    itemsCarrito = carrito,
                    onVolver = { navController.popBackStack() },
                    onIncrementar = { item ->
                        carrito = carrito.map {
                            if (it.producto.id == item.producto.id) it.copy(cantidad = it.cantidad + 1) else it
                        }
                    },
                    onDecrementar = { item ->
                        carrito = carrito.mapNotNull {
                            when {
                                it.producto.id != item.producto.id -> it
                                it.cantidad > 1 -> it.copy(cantidad = it.cantidad - 1)
                                else -> null
                            }
                        }
                    },
                    onEliminar = { item ->
                        carrito = carrito.filterNot { it.producto.id == item.producto.id }
                    },
                    onVaciarCarrito = {
                        carrito = emptyList()
                    },
                    onContinuarPedido = {
                        navController.navigate(Rutas.DATOS_ENTREGA)
                    }
                )
            }

            composable(Rutas.DATOS_ENTREGA) {
                val subtotal = carrito.sumOf { it.producto.precio * it.cantidad }
                DatosEntregaScreen(
                    subtotal = subtotal,
                    direccionInicial = clienteDireccion,
                    referenciaInicial = clienteReferencia,
                    onVolver = { navController.popBackStack() },
                    onConfirmarPedido = {
                        navController.navigate(Rutas.CONFIRMACION)
                    }
                )
            }

            composable(Rutas.CONFIRMACION) {
                val subtotal = carrito.sumOf { it.producto.precio * it.cantidad }
                ConfirmacionScreen(
                    numeroPedido = "#1024",
                    total = subtotal,
                    direccion = if (clienteDireccion.isNotEmpty()) clienteDireccion else "Av. Los Olivos 123",
                    referencia = if (clienteReferencia.isNotEmpty()) clienteReferencia else "Frente al parque",
                    onVerEstado = {
                        navController.navigate(Rutas.MIS_PEDIDOS)
                    },
                    onVolverInicio = {
                        carrito = emptyList()
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.INICIO) { inclusive = true }
                        }
                    }
                )
            }

            composable(Rutas.MIS_PEDIDOS) {
                MisPedidosScreen(
                    onVolver = { navController.popBackStack() }
                )
            }

            composable(Rutas.FAVORITOS) {
                FavoritosScreen(
                    onVolver = { navController.popBackStack() },
                    onProductoClick = { producto ->
                        navController.navigate(Rutas.detalle(producto.id))
                    },
                    onAgregarProducto = { producto ->
                        carrito = agregarOSumarProducto(carrito, producto, 1)
                    }
                )
            }

            composable(Rutas.PERFIL) {
                PerfilScreen(
                    nombreUsuario = clienteNombre,
                    esModoOscuro = esModoOscuro,
                    onCambiarModoOscuro = { esModoOscuro = it },
                    onVerPedidos = { navController.navigate(Rutas.MIS_PEDIDOS) },
                    onVerFavoritos = { navController.navigate(Rutas.FAVORITOS) },
                    onVolver = { navController.popBackStack() }
                )
            }
        }

        if (mostrarTerminos) {
            AlertDialog(
                onDismissRequest = { mostrarTerminos = false },
                title = { Text(text = "Términos y Condiciones") },
                text = {
                    Text(
                        text = "Bienvenido a Mi Bodega. Al utilizar nuestra aplicación aceptas el uso de tus datos " +
                                "únicamente para el procesamiento de tus pedidos y envíos a domicilio."
                    )
                },
                confirmButton = {
                    TextButton(onClick = { mostrarTerminos = false }) {
                        Text("Entendido")
                    }
                }
            )
        }
    }
}

private fun agregarOSumarProducto(
    carrito: List<ItemCarrito>,
    producto: Producto,
    cantidad: Int
): List<ItemCarrito> {
    val itemExistente = carrito.find { it.producto.id == producto.id }
    return if (itemExistente != null) {
        carrito.map {
            if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + cantidad) else it
        }
    } else {
        carrito + ItemCarrito(producto = producto, cantidad = cantidad)
    }
}