package com.antoniowalls.indriverstudents.presentation.screens.auth.login.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.antoniowalls.indriverstudents.domain.util.Resource
import com.antoniowalls.indriverstudents.presentation.components.ProgressBar
import com.antoniowalls.indriverstudents.presentation.screens.auth.login.LoginViewModel

@Composable
fun Login(vm: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current

    when(val response= vm.loginResponse){
        Resource.Loading -> {
            //en caso de que la respuesta sea loading al usuario le muestra algo en pantalla de que debe esperar
            ProgressBar()
        }
        is Resource.Success -> {
            Toast.makeText(context, "Login Exitoso", Toast.LENGTH_LONG).show()
        }
        is Resource.Failure -> {
            Toast.makeText(context, response.message, Toast.LENGTH_LONG).show() //muestra un mensaje de error desde el servidor

        }
        else -> {
            if (response != null) {
                Toast.makeText(context, "Hubo un error desconocido", Toast.LENGTH_LONG).show()
            }
        }
    }
}