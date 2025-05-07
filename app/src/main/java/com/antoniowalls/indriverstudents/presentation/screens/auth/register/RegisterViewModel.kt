package com.antoniowalls.indriverstudents.presentation.screens.auth.register

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.domain.useCases.auth.AuthUseCases
import com.antoniowalls.indriverstudents.domain.util.Resource
import com.antoniowalls.indriverstudents.presentation.screens.auth.register.mapper.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel //todo ViewModel necesita este arroba para poderse inyectar en otras clases
class RegisterViewModel @Inject constructor(private val authUseCases: AuthUseCases): ViewModel() { //y siempre se van declarar las clases de esta manera
    var state by mutableStateOf(RegisterState()) //esta cosa de aquí sirve para controlar los estados
        private set
    var errorMessage by mutableStateOf("") //variable para recoger los errores

    var registerResponse by mutableStateOf<Resource<AuthResponse>?>(null)
        private set

    fun register()= viewModelScope.launch{ // el viewModelScope.launch sirve para que se ejecute una corrutina dentro del viewModel
        if(isValidForm()){
            registerResponse = Resource.Loading
            val result = authUseCases.register(state.toUser())
            registerResponse = result //SUCCESS o FAILURE
        }
        else{
            Log.d("RegisterViewModel", errorMessage)
        }
    }
    fun saveSession(authResponse: AuthResponse) = viewModelScope.launch {
        authUseCases.saveSession(authResponse)
    }

    fun onNameInput(name: String){
        state = state.copy(name = name)
    }

    fun onLastnameInput(lastname: String){
        state = state.copy(lastname = lastname)
    }

    fun onEmailInput(email: String){
        state = state.copy(email = email)
    }

    fun onPhoneInput(phone: String){
        state = state.copy(phone = phone)
    }

    fun onPasswordInput(password: String){
        state = state.copy(password = password)
    }

    fun onConfirmPasswordInput(confirmPassword: String){
        state = state.copy(confirmPassword = confirmPassword)

    }
 // este formulario valida todos los estados si es que son validos o no por medio de diferentes estados usando la variable errorMessage
    fun isValidForm(): Boolean{
        errorMessage = ""
        if(state.name.isEmpty()){
            errorMessage = "Ingresa un nombre"
            return false
        }
        else if(state.lastname.isEmpty()){
            errorMessage = "Ingresa un apellido"
            return false
        }
        else if(state.email.isEmpty()){
            errorMessage = "Ingresa un correo electrónico"
            return false
        }
        else if(state.phone.isEmpty()){
            errorMessage = "Ingresa un número de teléfono"
            return false
        }
        else if(state.password.isEmpty()){
            errorMessage = "Ingresa una contraseña"
            return false
        }
        else if(state.confirmPassword.isEmpty()){
            errorMessage = "Confirma tu contraseña"
            return false
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()){ //patterns es una función internat de android para validar el email, hace uso de expresiones regulares
            errorMessage = "ingresa un correo electrónico válido"
            return false
        }
        else if(state.password.length < 6){
            errorMessage = "La contraseña debe de tener al menos 6 caracteres"
            return false
        }
        else if(state.password != state.confirmPassword){
            errorMessage = "Las contraseñas no coinciden"
            return false
        }
        return true
    }

}