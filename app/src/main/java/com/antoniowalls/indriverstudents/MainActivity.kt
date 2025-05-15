package com.antoniowalls.indriverstudents

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.antoniowalls.indriverstudents.presentation.navigation.graph.root.RootNavGraph
import com.antoniowalls.indriverstudents.ui.theme.InDriverStudentsTheme
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Obtener el valor real de la API Key desde recursos
        val apiKey = getString(R.string.google_maps_key)
        if (apiKey.isBlank()) {
            Log.e("MainActivity", "API Key no encontrada. Verifica tu configuración.")
            finish()
            return
        }

        // 2. Inicializar Places solo si no está ya inicializado
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        // 3. Configuración de la UI con Jetpack Compose
        enableEdgeToEdge()
        setContent {
            InDriverStudentsTheme {
                navController = rememberNavController()
                RootNavGraph(navHostController = navController)
            }
        }
    }
}
