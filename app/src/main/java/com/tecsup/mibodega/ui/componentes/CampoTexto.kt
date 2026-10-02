package com.tecsup.mibodega.ui.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun CampoTexto(
    etiqueta: String,
    valor: String,
    onValorCambia: (String) -> Unit,
    placeholder: String = "",
    esContrasena: Boolean = false,
    esError: Boolean = false,
    mensajeError: String? = null
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorCambia,
        label = { Text(etiqueta) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        isError = esError,
        supportingText = {
            if (esError && mensajeError != null) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        visualTransformation = if (esContrasena) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = GrisClaro,
            focusedContainerColor = GrisClaro,
            unfocusedBorderColor = if (esError) MaterialTheme.colorScheme.error else androidx.compose.ui.graphics.Color.Transparent,
            focusedBorderColor = if (esError) MaterialTheme.colorScheme.error else VerdeBodega,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorContainerColor = GrisClaro
        )
    )
}