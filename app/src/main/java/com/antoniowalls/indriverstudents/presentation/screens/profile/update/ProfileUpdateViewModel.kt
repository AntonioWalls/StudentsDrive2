package com.antoniowalls.indriverstudents.presentation.screens.profile.update

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antoniowalls.indriverstudents.domain.model.AuthResponse
import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.domain.useCases.auth.AuthUseCases
import com.antoniowalls.indriverstudents.domain.useCases.user.UserUseCases
import com.antoniowalls.indriverstudents.domain.util.Resource
import com.antoniowalls.indriverstudents.presentation.screens.profile.update.mapper.toUser
import com.antoniowalls.indriverstudents.presentation.util.ComposeFileProvider
import com.antoniowalls.indriverstudents.presentation.util.ResultingActivityHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel@Inject constructor(
    private val authUseCases: AuthUseCases,
    private val userUseCases: UserUseCases,
    private val savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
): ViewModel() {
    var state by mutableStateOf(ProfileUpdateState())
        private set
    val data = savedStateHandle.get<String>("user")
    val user = User.fromJson(data!!)
    var updateResponse by mutableStateOf<Resource<User>?>(null)
        private set

    var file : File? = null
    val resultingActivityHandler = ResultingActivityHandler()

    init {
        state = state.copy(
            name = user.name,
            lastname = user.lastname,
            phone = user.phone,
            image = user.image
        )
    }

    fun submit(){
        if(file == null){
         update()
        } else{
            updateWithImage()
        }
    }

    fun updateUserSession(userResponse: User)=viewModelScope.launch{
        authUseCases.updateSessionUseCase(userResponse)
    }

    fun updateWithImage () = viewModelScope.launch {
        updateResponse = Resource.Loading
        val result = userUseCases.update(user.id.toString(), state.toUser(), file)
        updateResponse = result
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

    fun pickImage() = viewModelScope.launch {
        val result = resultingActivityHandler.getContent("image/*")
        if(result!=null){
            file = ComposeFileProvider.createFileFromUri(context, result)
            state = state.copy(image = result.toString())
        }
    }

    fun takePhoto() = viewModelScope.launch {
        val result = resultingActivityHandler.takePicturePreview()
        if(result!=null){
            state = state.copy(image = ComposeFileProvider.getPathFromBitmap(context, result))
            file = File(state.image)
        }
    }

}
