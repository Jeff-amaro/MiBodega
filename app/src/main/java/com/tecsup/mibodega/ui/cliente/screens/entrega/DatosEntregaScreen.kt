package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class TipoEntrega { RECOJO_TIENDA, DELIVERY }
enum class MetodoPago { EFECTIVO, YAPE, PLIN }

@Composable
fun DatosEntregaScreen(
    subtotal: Double,
    direccionInicial: String = "",
    referenciaInicial: String = "",
    onVolver: () -> Unit,
    onConfirmarPedido: (tipoEntrega: String, metodoPago: String, costoTotal: Double) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf(direccionInicial) }
    var referencia by remember { mutableStateOf(referenciaInicial) }

    var tipoEntrega by remember { mutableStateOf(TipoEntrega.DELIVERY) }
    var metodoPago by remember { mutableStateOf(MetodoPago.EFECTIVO) }
    var intentarAvanzar by remember { mutableStateOf(false) }

    val costoEnvio = if (tipoEntrega == TipoEntrega.DELIVERY) 5.00 else 0.00
    val total = subtotal + costoEnvio

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onVolver) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "Datos de Entrega y Pago",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // SECCIÓN: Método de Envío
            Text(
                text = "1. Método de Envío",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { tipoEntrega = TipoEntrega.DELIVERY }
            ) {
                RadioButton(
                    selected = tipoEntrega == TipoEntrega.DELIVERY,
                    onClick = { tipoEntrega = TipoEntrega.DELIVERY },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = "Delivery (+S/ 5.00)",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { tipoEntrega = TipoEntrega.RECOJO_TIENDA }
            ) {
                RadioButton(
                    selected = tipoEntrega == TipoEntrega.RECOJO_TIENDA,
                    onClick = { tipoEntrega = TipoEntrega.RECOJO_TIENDA },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = "Recojo en tienda (Gratis)",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(16.dp))

            // SECCIÓN: Datos de Contacto y Dirección
            Text(
                text = "2. Datos de Contacto",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre Completo") },
                isError = intentarAvanzar && nombre.isBlank(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
                supportingText = {
                    if (intentarAvanzar && nombre.isBlank()) {
                        Text("Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono / Celular") },
                isError = intentarAvanzar && telefono.isBlank(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
                supportingText = {
                    if (intentarAvanzar && telefono.isBlank()) {
                        Text("Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            if (tipoEntrega == TipoEntrega.DELIVERY) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección de entrega") },
                    isError = intentarAvanzar && direccion.isBlank(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        errorBorderColor = MaterialTheme.colorScheme.error
                    ),
                    supportingText = {
                        if (intentarAvanzar && direccion.isBlank()) {
                            Text("Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = referencia,
                    onValueChange = { referencia = it },
                    label = { Text("Referencia (Opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(16.dp))

            // SECCIÓN: Método de Pago (Efectivo, Yape, Plin)
            Text(
                text = "3. Método de Pago",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { metodoPago = MetodoPago.EFECTIVO }
            ) {
                RadioButton(
                    selected = metodoPago == MetodoPago.EFECTIVO,
                    onClick = { metodoPago = MetodoPago.EFECTIVO },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                )
                Text("💵 Efectivo (Contra entrega)", fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { metodoPago = MetodoPago.YAPE }
            ) {
                RadioButton(
                    selected = metodoPago == MetodoPago.YAPE,
                    onClick = { metodoPago = MetodoPago.YAPE },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                )
                Text("📱 Yape (987 654 321)", fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { metodoPago = MetodoPago.PLIN }
            ) {
                RadioButton(
                    selected = metodoPago == MetodoPago.PLIN,
                    onClick = { metodoPago = MetodoPago.PLIN },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                )
                Text("💳 Plin (987 654 321)", fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Resumen de Montos
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Subtotal: S/ ${String.format("%.2f", subtotal)}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Envío: S/ ${String.format("%.2f", costoEnvio)}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Total a pagar: S/ ${String.format("%.2f", total)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    intentarAvanzar = true
                    val camposValidos = if (tipoEntrega == TipoEntrega.DELIVERY) {
                        nombre.isNotBlank() && telefono.isNotBlank() && direccion.isNotBlank()
                    } else {
                        nombre.isNotBlank() && telefono.isNotBlank()
                    }

                    if (camposValidos) {
                        val textoEnvio = if (tipoEntrega == TipoEntrega.DELIVERY) "Delivery" else "Recojo en Tienda"
                        val textoPago = when (metodoPago) {
                            MetodoPago.EFECTIVO -> "Efectivo"
                            MetodoPago.YAPE -> "Yape"
                            MetodoPago.PLIN -> "Plin"
                        }
                        onConfirmarPedido(textoEnvio, textoPago, total)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Confirmar y Realizar Pedido", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}