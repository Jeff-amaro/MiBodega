package com.tecsup.mibodega.ui.cliente.modelo

data class PedidoHistorial(
    val id: String,
    val fecha: String,
    val total: Double,
    val estado: String,
    val metodoEntrega: String
)