package com.tecsup.mibodega.ui.cliente.screens.bienvenida

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.R
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.BotonSecundario
import com.tecsup.mibodega.ui.componentes.CampoTexto

@Composable
fun BienvenidaScreen(
    onRegistrarse: () -> Unit,
    onIniciarSesion: () -> Unit,
    onTerminos: () -> Unit
) {
    var mostrarModalLogin by remember { mutableStateOf(false) }
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorLogin by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo Mi Bodega",
            modifier = Modifier.size(120.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Mi Bodega",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Tus productos de siempre en la puerta de tu casa",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.weight(1f))

        BotonPrimario(
            texto = "Registrarme",
            onClick = onRegistrarse
        )

        Spacer(Modifier.height(12.dp))

        BotonSecundario(
            texto = "Iniciar sesión",
            onClick = { mostrarModalLogin = true }
        )

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onTerminos) {
            Text("Términos y Condiciones", style = MaterialTheme.typography.labelMedium)
        }
    }

    if (mostrarModalLogin) {
        AlertDialog(
            onDismissRequest = { mostrarModalLogin = false },
            title = { Text("Iniciar sesión") },
            text = {
                Column {
                    CampoTexto(
                        etiqueta = "Usuario",
                        valor = usuario,
                        onValorCambia = { usuario = it },
                        placeholder = "cliente"
                    )
                    Spacer(Modifier.height(8.dp))
                    CampoTexto(
                        etiqueta = "Contraseña",
                        valor = contrasena,
                        onValorCambia = { contrasena = it },
                        placeholder = "123",
                        esContrasena = true
                    )
                    if (errorLogin != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = errorLogin!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Credenciales fijas de prueba: usuario="cliente", clave="123"
                        if (usuario == "cliente" && contrasena == "123") {
                            mostrarModalLogin = false
                            onIniciarSesion()
                        } else {
                            errorLogin = "Usuario o contraseña incorrectos"
                        }
                    }
                ) {
                    Text("Ingresar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarModalLogin = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}