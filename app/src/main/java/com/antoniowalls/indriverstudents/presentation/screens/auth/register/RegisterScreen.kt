package com.antoniowalls.indriverstudents.presentation.screens.auth.register

import android.inputmethodservice.Keyboard.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.antoniowalls.indriverstudents.R
import com.antoniowalls.indriverstudents.presentation.components.DefaultButton
import com.antoniowalls.indriverstudents.presentation.components.DefaultOutlinedTextField
import androidx.compose.foundation.layout.Row
import com.antoniowalls.indriverstudents.presentation.screens.auth.register.components.RegisterContent


@Composable
fun RegisterScreen(navHostController: NavHostController) {

    Scaffold{ paddingValues ->
        RegisterContent(navHostController = navHostController, paddingValues = paddingValues)
    }
}

@Preview(showBackground = true, showSystemUi = true) // Anotación para habilitar la vista previa
@Composable
fun RegisterScreenPreview() {
    // Para la vista previa, generalmente creas un NavController de prueba o
    // modificas tu Composable para aceptar uno nulo si no es esencial para la UI básica.
    // Aquí usamos rememberNavController() para simplicidad de la vista previa.
    val navController = rememberNavController()
    RegisterScreen(navHostController = navController)
}