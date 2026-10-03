package com.tecsup.mibodega.ui.cliente.screens.registro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegistroScreen(
    esNuevoRegistro: Boolean,
    onVolver: () -> Unit,
    onConfirmar: (nombre: String, telefono: String, direccion: String, referencia: String) -> Unit
) {
    var nombre by remember { mutableStateOf(if (esNuevoRegistro) "Juan Pérez" else "") }
    var telefono by remember { mutableStateOf(if (esNuevoRegistro) "987 654 321" else "") }
    var direccion by remember { mutableStateOf(if (esNuevoRegistro) "Av. Los Olivos 123" else "") }
    var referencia by remember { mutableStateOf(if (esNuevoRegistro) "Frente al parque" else "") }
    var contrasena by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        IconButton(onClick = onVolver) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver"
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = if (esNuevoRegistro) "Crear cuenta" else "Iniciar sesión",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = if (esNuevoRegistro) "Completa tus datos para continuar" else "Ingresa tus credenciales para ingresar",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(20.dp))

        if (esNuevoRegistro) {
            // Diseño de Crear Cuenta con Avatar
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(color = Color(0xFFD0E1F9), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            modifier = Modifier.size(60.dp),
                            tint = Color(0xFF2B5B84)
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Agregar foto",
                        modifier = Modifier.size(32.dp),
                        tint = Color(0xFF3B72A4)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            CampoTexto(
                etiqueta = "Nombre completo",
                valor = nombre,
                onValorChange = { nombre = it }
            )

            Spacer(Modifier.height(14.dp))

            CampoTexto(
                etiqueta = "Teléfono",
                valor = telefono,
                onValorChange = { telefono = it }
            )

            Spacer(Modifier.height(14.dp))

            CampoTexto(
                etiqueta = "Dirección de entrega",
                valor = direccion,
                onValorChange = { direccion = it }
            )

            Spacer(Modifier.height(14.dp))

            CampoTexto(
                etiqueta = "Referencia",
                valor = referencia,
                onValorChange = { referencia = it }
            )
        } else {
            // Formulario de Iniciar Sesión (Usuario/Teléfono + Contraseña)
            CampoTexto(
                etiqueta = "Teléfono o Usuario",
                valor = telefono,
                onValorChange = { telefono = it }
            )

            Spacer(Modifier.height(16.dp))

            Column {
                Text(
                    text = "Contraseña",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2E7D32)
                    ),
                    singleLine = true
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = { onConfirmar(nombre, telefono, direccion, referencia) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text(
                text = if (esNuevoRegistro) "Crear cuenta" else "Ingresar",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun CampoTexto(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit
) {
    Column {
        Text(
            text = etiqueta,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF222222),
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedBorderColor = Color(0xFF2E7D32)
            ),
            singleLine = true
        )
    }
}