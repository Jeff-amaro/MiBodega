package com.tecsup.mibodega.ui.cliente.screens.bienvenida

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.R

@Composable
fun BienvenidaScreen(
    onRegistrarse: () -> Unit,
    onIniciarSesion: () -> Unit,
    onTerminos: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 28.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.height(10.dp))

        // 1. Logo Bodega Hub
        Image(
            painter = painterResource(id = R.drawable.ilustracion_bodega),
            contentDescription = "Logo Bodega Hub",
            modifier = Modifier
                .size(240.dp)
                .clip(RoundedCornerShape(20.dp))
        )

        // 2. Título bicolor
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF1C1B1F), fontWeight = FontWeight.Bold, fontSize = 34.sp)) {
                        append("Mi ")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 34.sp)) {
                        append("Bodega")
                    }
                }
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Tus productos de siempre\nen la puerta de tu casa",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                ),
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        // 3. Botones según la estructura requerida
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onRegistrarse,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Teléfono",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Registrarme",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "con mi teléfono",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = onIniciarSesion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Al continuar aceptas nuestros",
                fontSize = 12.sp,
                color = Color.Gray
            )
            TextButton(
                onClick = onTerminos,
                modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = "Términos y Condiciones",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
            }
        }
    }
}