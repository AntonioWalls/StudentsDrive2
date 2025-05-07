package com.antoniowalls.indriverstudents.presentation.screens.auth.login

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import com.antoniowalls.indriverstudents.domain.useCases.auth.AuthUseCases
import com.antoniowalls.indriverstudents.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//Aquí en el viewModel es donde tendremos los estados del login
@HiltViewModel
class LoginViewModel @Inject constructor(private val authUseCase: AuthUseCases) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    var errorMessage by mutableStateOf("")

    var loginResponse by mutableStateOf<Resource<AuthResponse>?>(null)
        private set

    init{
        getSessionData()
    }

    fun onEmailInput(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordInput(password: String) {
        state = state.copy(password = password)
    }

    fun login() = viewModelScope.launch { //todo lo que está dentro de viewModelScope.launch es una corrutina y se ejecuta de manera asincrona
        if (isValidForm()) {
            loginResponse = Resource.Loading
            val result = authUseCase.login(state.email, state.password)
            loginResponse = result //SUCCEESS o FAILURE
        } else {
            Log.d("LoginViewModel", errorMessage)

        }
    }

    fun getSessionData() = viewModelScope.launch {
        authUseCase.getSessionData().collect(){ data ->
            Log.d("LoginViewModel", "Datos de sesión: $data")
            if(!data.token.isNullOrBlank()){ //token de sesión existe y además no está vacío
                loginResponse = Resource.Success(data)
            }

        }
    }

    fun saveSession(authResponse: AuthResponse) = viewModelScope.launch {
        authUseCase.saveSession(authResponse)
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