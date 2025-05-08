package com.antoniowalls.indriverstudents.presentation.screens.profile.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.domain.useCases.user.UserUseCases
import com.antoniowalls.indriverstudents.domain.util.Resource
import com.antoniowalls.indriverstudents.presentation.screens.profile.update.mapper.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel@Inject constructor(
    private val userUseCases: UserUseCases,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    var state by mutableStateOf(ProfileUpdateState())
        private set
    val data = savedStateHandle.get<String>("user")
    val user = User.fromJson(data!!)

    var updateResponse by mutableStateOf<Resource<User>?>(null)
        private set

    init {
        state = state.copy(
            name = user.name,
            lastname = user.lastname,
            phone = user.phone,
            image = user.image
        )
    }

    fun update () = viewModelScope.launch {
        updateResponse = Resource.Loading
        val result = userUseCases.update(user.id.toString(), state.toUser(), null)
        updateResponse = result
    }

    fun onNameInput(name: String){
        state = state.copy(name = name)
    }

    fun onLastNameInput(lastname: String){
        state = state.copy(lastname = lastname)
    }

    fun onPhoneInput(phone: String){
        state = state.copy(phone = phone)
    }

}
