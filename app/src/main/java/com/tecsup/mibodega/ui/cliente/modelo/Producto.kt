package com.tecsup.mibodega.ui.cliente.modelo

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String = "",
    val categoria: String = "",
    val precio: Double = 0.0,
    val peso: String = "",
    val imagenUrl: String = "",
    val esFavorito: Boolean = false
)