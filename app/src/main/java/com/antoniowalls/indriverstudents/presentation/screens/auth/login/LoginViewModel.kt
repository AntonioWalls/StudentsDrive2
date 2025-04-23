package com.antoniowalls.indriverstudents.presentation.screens.auth.login

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//Aquí en el viewModel es donde tendremos los estados del login
@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    var errorMessage by mutableStateOf("")

    fun onEmailInput(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordInput(password: String) {
        state = state.copy(password = password)
    }

    fun login() {
        if (isValidForm()) {
            Log.d("LoginViewModel", "Email: ${state.email}, Password: ${state.password}")
        } else {
            Log.d("LoginViewModel", errorMessage)

        }
    }

    fun isValidForm(): Boolean{
        errorMessage = ""

        if(state.email.isEmpty()){
            errorMessage = "Ingresa un correo electrónico"
            return false
        }
        else if(state.password.isEmpty()){
            errorMessage = "Ingresa una contraseña"
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()){
            errorMessage = "Ingresa un correo electrónico válido"
            return false
        }
        else if (state.password.length < 6){
            errorMessage = "La contraseña debe tener al menos 6 caracteres"
            return false
        }

        return true
    }
}