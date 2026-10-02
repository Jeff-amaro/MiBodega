package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto

@Composable
fun DatosEntregaScreen(
    total: Double,
    direccionInicial: String = "",
    referenciaInicial: String = "",
    onVolver: () -> Unit,
    onConfirmarPedido: () -> Unit
) {
    var direccion by remember { mutableStateOf(direccionInicial) }
    var referencia by remember { mutableStateOf(referenciaInicial) }
    var metodoPago by remember { mutableStateOf("Efectivo") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text("Datos de entrega", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Dirección de entrega",
            valor = direccion,
            onValorCambia = { direccion = it },
            placeholder = "Av.Metropolitano"
        )
        Spacer(Modifier.height(12.dp))

        CampoTexto(
            etiqueta = "Referencia",
            valor = referencia,
            onValorCambia = { referencia = it },
            placeholder = "Frente al parque"
        )

        Spacer(Modifier.height(20.dp))

        Text("Método de pago", fontWeight = FontWeight.Bold)
        listOf("Efectivo", "Yape / Plin").forEach { opcion ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (metodoPago == opcion),
                    onClick = { metodoPago = opcion }
                )
                Text(opcion)
            }
        }

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Total: ", style = MaterialTheme.typography.titleMedium)
            Text(
                text = "S/ %.2f".format(total),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(12.dp))
        BotonPrimario(
            texto = "Realizar pedido",
            onClick = onConfirmarPedido
        )
    }
}