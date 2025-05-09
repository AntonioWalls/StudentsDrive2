package com.antoniowalls.indriverstudents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.antoniowalls.indriverstudents.presentation.navigation.graph.root.RootNavGraph
import com.antoniowalls.indriverstudents.presentation.screens.auth.login.LoginScreen
import com.antoniowalls.indriverstudents.ui.theme.InDriverStudentsTheme
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!Places.isInitialized()){
            Places.initialize(applicationContext, BuildConfig.Maps_API_KEY)
        }
        enableEdgeToEdge()
        setContent {
            InDriverStudentsTheme {
                Surface {
                    navController = rememberNavController()
                    RootNavGraph(navHostController = navController)
                }
            }
        }
    }
}