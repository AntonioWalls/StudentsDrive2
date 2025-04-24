package com.antoniowalls.indriverstudents.presentation.screens.auth.register

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel //todo ViewModel necesita este arroba para poderse inyectar en otras clases
class RegisterViewModel @Inject constructor(): ViewModel() { //y siempre se van declarar las clases de esta manera
    var state by mutableStateOf(RegisterState()) //esta cosa de aquí sirve para controlar los estados
        private set
    var errorMessage by mutableStateOf("") //variable para recoger los errores

    fun register(){
        if(isValidForm()){
            Log.d("RegisterViewModel", "Name: ${state.name}")
            Log.d("RegisterViewModel", "LastName: ${state.lastname}")
            Log.d("RegisterViewModel", "Email: ${state.email}")
            Log.d("RegisterViewModel", "Phone: ${state.phone}")
            Log.d("RegisterViewModel", "Password: ${state.password}")
            Log.d("RegisterViewModel", "ConfirmPassword: ${state.confirmPassword}")
        }
        else{
            Log.d("RegisterViewModel", errorMessage)
        }
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