package com.antoniowalls.indriverstudents.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun DefaultOutlinedTextField( // Mantenemos el nombre original
    modifier: Modifier,
    value: String,
    label: String, // Este texto irá al placeholder
    icon: ImageVector,
    onValueChange: (value: String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    hideText: Boolean = false
) {
    // Define los colores personalizados en blanco
    // Asegúrate de usar OutlinedTextFieldDefaults de androidx.compose.material3
    val customTextFieldColors = OutlinedTextFieldDefaults.colors(
        // Color del texto cuando se escribe
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        // Color del cursor
        cursorColor = Color.White,
        // Color del borde
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.White.copy(alpha = 0.7f), // Borde ligeramente transparente cuando no está enfocado
        // Color del icono
        focusedLeadingIconColor = Color.White,
        unfocusedLeadingIconColor = Color.White.copy(alpha = 0.7f), // Icono ligeramente transparente cuando no está enfocado
        // Color del placeholder
        focusedPlaceholderColor = Color.White.copy(alpha = 0.5f), // Placeholder más transparente cuando está enfocado (opcional)
        unfocusedPlaceholderColor = Color.White.copy(alpha = 0.7f) // Placeholder ligeramente transparente cuando no está enfocado
        // Puedes ajustar otros colores si es necesario (fondo, error, etc.)
        // errorBorderColor = Color.Red, // Ejemplo si quieres color de error diferente
        // errorLeadingIconColor = Color.Red
    )

    Box(
        // Aplicas el modifier al Box según tu código original
        modifier = modifier.height(55.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(), // Hacemos que el TextField llene el Box
            value = value,
            onValueChange = { text ->
                onValueChange(text)
            },
            // Se añade el parámetro 'placeholder' usando el texto de 'label'
            placeholder = {
                Text(
                    text = label
                    // El color lo define 'customTextFieldColors'
                )
            },
            leadingIcon = {
                // Usamos la misma estructura de Row que tenías
                Row (verticalAlignment = Alignment.CenterVertically) { // Alinear verticalmente
                    Icon(
                        imageVector = icon,
                        contentDescription = null // Añadir descripción es buena práctica
                        // El color del icono lo define 'customTextFieldColors'
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Separador vertical ahora en blanco sólido
                    Spacer(
                        modifier = Modifier
                            .height(24.dp) // Altura sugerida
                            .width(1.dp)
                            // Cambia el fondo del separador a blanco sólido (sin alpha)
                            .background(Color.White) // <--- CAMBIO AQUÍ: quitado el .copy(alpha = 0.7f)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (hideText) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true, // A menudo útil
            // Aplica los colores personalizados definidos arriba
            colors = customTextFieldColors
        )
    }
}

