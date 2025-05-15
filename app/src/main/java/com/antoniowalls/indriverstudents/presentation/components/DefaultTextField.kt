package com.antoniowalls.indriverstudents.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun DefaultTextField(
    // 1) El modifier sigue igual, con valor por defecto
    modifier: Modifier = Modifier,
    // 2) Parámetro nuevo, opcional, para el FocusRequester
    focusRequester: FocusRequester? = null,
    // 3) Otro opcional para focusTarget()
    useFocusTarget: Boolean = false,
    value: String,
    label: String,
    icon: ImageVector,
    onValueChange: (value: String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    hideText: Boolean = false,
    enabled: Boolean = true,
    background: Color = Color.White
) {

  Box(
      modifier = Modifier
          .height(60.dp)
          .background(
              color = background,
              shape = RoundedCornerShape(topStart = 15.dp, bottomEnd = 15.dp)
          )
  ) {
      TextField(
          modifier = modifier
              .then( if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier )
              .then( if (useFocusTarget) Modifier.focusTarget() else Modifier ),
          value = value,
          onValueChange = {text ->
              onValueChange(text)
          },
          label = {
              Text(
                  text = label,
                  color = Color.Black
              )
          },
          enabled = enabled,
          leadingIcon = {
              Row () {
                  Icon(
                      imageVector = icon,
                      contentDescription = null,
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Spacer(modifier = Modifier
                      .height(20.dp)
                      .width(1.dp)
                      .background(Color.Black))
              }

          },
          keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
          visualTransformation = if(hideText) PasswordVisualTransformation() else VisualTransformation.None,
          colors = TextFieldDefaults.colors(
              // --- Color del Texto ---
              focusedTextColor = Color.Black,
              unfocusedTextColor = Color.Black,

              // --- Colores del Contenedor (Fondo del TextField) ---
              focusedContainerColor = Color.Transparent,
              unfocusedContainerColor = Color.Transparent,
              disabledContainerColor = Color.Transparent,

              // --- Colores del Indicador (línea inferior) ---
              focusedIndicatorColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Transparent,
              disabledIndicatorColor = Color.Transparent,
              errorIndicatorColor = Color.Transparent,

              // --- Colores de Iconos y Etiqueta ---
              // CAMBIO: Icono negro cuando está enfocado
              focusedLeadingIconColor = Color.Black,
              // CAMBIO: Icono gris cuando NO está enfocado
              unfocusedLeadingIconColor = Color.Gray,
              focusedLabelColor = Color(0xFFB1B1B1),
              unfocusedLabelColor = Color(0xFFB1B1B1),

              cursorColor = Color.Black
          )

      )
  }
}

