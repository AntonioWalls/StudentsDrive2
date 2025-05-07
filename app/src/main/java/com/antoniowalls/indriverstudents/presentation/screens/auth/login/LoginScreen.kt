package com.antoniowalls.indriverstudents.presentation.screens.auth.login
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.antoniowalls.indriverstudents.presentation.navigation.screen.auth.AuthScreen.Login
import com.antoniowalls.indriverstudents.presentation.screens.auth.login.components.Login
import com.antoniowalls.indriverstudents.presentation.screens.auth.login.components.LoginContent


@Composable
fun LoginScreen(navHostController: NavHostController) {
    Scaffold (
        contentWindowInsets = WindowInsets.navigationBars
    ){ paddingValues -> //aquí se diseña  como si fuera un HTML
        LoginContent(navHostController = navHostController, paddingValues = paddingValues)
    }
    Login(navHostController)
}

// --- Función de Preview ---
@Preview(showSystemUi = true, name = "Login Screen Preview") // showSystemUi muestra barras de estado/navegación
@Composable
fun LoginScreenPreview() {
    // Crea un NavController falso para el preview
    val navController = rememberNavController()
    // Llama a tu LoginScreen pasando el NavController falso
    // Podrías envolver esto en tu tema de la app si tienes uno: TuAppTheme { LoginScreen(...) }
    LoginScreen(navHostController = navController)
}