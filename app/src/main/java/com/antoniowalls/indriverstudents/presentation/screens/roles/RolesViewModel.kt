package com.antoniowalls.indriverstudents.presentation.screens.roles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import com.antoniowalls.indriverstudents.domain.useCases.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RolesViewModel@Inject constructor(private val authUseCases: AuthUseCases)  :ViewModel() {

    var authResponse by mutableStateOf(AuthResponse())

    init {
        getSessionData()
    }

    fun getSessionData() = viewModelScope.launch {
        authUseCases.getSessionData().collect { data->
            if(!data.token.isNullOrBlank()){
                authResponse = data
            }
        }
    }



}