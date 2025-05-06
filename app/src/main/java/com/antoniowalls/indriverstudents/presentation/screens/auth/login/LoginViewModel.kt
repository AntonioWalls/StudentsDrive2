package com.antoniowalls.indriverstudents.presentation.screens.auth.login

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniowalls.indriverstudents.domain.useCases.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//Aquí en el viewModel es donde tendremos los estados del login
@HiltViewModel
class LoginViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    var errorMessage by mutableStateOf("")

    fun onEmailInput(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordInput(password: String) {
        state = state.copy(password = password)
    }

    fun login() = viewModelScope.launch { //todo lo que está dentro de viewModelScope.launch es una corrutina y se ejecuta de manera asincrona
        if (isValidForm()) {

            val result = authUseCase.login(state.email, state.password)

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