package com.antoniowalls.indriverstudents.presentation.screens.auth.register.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.antoniowalls.indriverstudents.domain.util.Resource
import com.antoniowalls.indriverstudents.presentation.components.ProgressBar
import com.antoniowalls.indriverstudents.presentation.navigation.Graph
import com.antoniowalls.indriverstudents.presentation.screens.auth.register.RegisterViewModel

@Composable
fun Register(navHostController: NavHostController,vm: RegisterViewModel = hiltViewModel()) {
    val context = LocalContext.current

    when(val response = vm.registerResponse){
        Resource.Loading -> {
            ProgressBar()
        }
        is Resource.Success -> {
            LaunchedEffect(Unit) {
                vm.saveSession(response.data)
                navHostController.navigate(Graph.CLIENT){
                    popUpTo(Graph.AUTH) {inclusive = true}
                }
            }
        }
        is Resource.Failure -> {
            Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
        }
        else -> {
            if (response != null) {
                Toast.makeText(context, "Hubo un error desconocido", Toast.LENGTH_LONG).show()
            }
        }
    }
}