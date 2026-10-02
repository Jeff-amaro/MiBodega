package com.tecsup.mibodega.ui.cliente

sealed class Ruta(val ruta: String) {
    object Bienvenida : Ruta("bienvenida")
    object Registro : Ruta("registro")
    object Inicio : Ruta("inicio")
    object DetalleProducto : Ruta("detalle_producto/{productoId}") {
        fun crearRuta(productoId: Int) = "detalle_producto/$productoId"
    }
    object Carrito : Ruta("carrito")
    object DatosEntrega : Ruta("datos_entrega")
    object Confirmacion : Ruta("confirmacion")
}