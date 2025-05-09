package com.antoniowalls.indriverstudents.data.repository

import com.antoniowalls.indriverstudents.data.dataSource.remote.service.UserService
import com.antoniowalls.indriverstudents.data.util.HandleRequest
import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.domain.repository.UserRepository
import com.antoniowalls.indriverstudents.domain.util.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody // Importación necesaria para asRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull // Importación necesaria para toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody // Importación necesaria para toRequestBody en String
import java.io.File

class UserRepositoryImpl(private val userService: UserService): UserRepository {
    override suspend fun update(id: String, user: User, file: File?): Resource<User> {
        if(file != null){ //actualizar con imagen
            val connection = file.toURI().toURL().openConnection()
            val mimeType = connection.contentType //esto sirve para saber si es una imagen jpg o png
            val contentType = "text/plain"
            val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
            val fileFormData = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val nameData = user.name.toRequestBody(contentType.toMediaTypeOrNull())
            val lastnameData = user.lastname.toRequestBody(contentType.toMediaTypeOrNull())
            val phoneData = user.phone.toRequestBody(contentType.toMediaTypeOrNull())
            val result = userService.updateWithImage(fileFormData, id, nameData, lastnameData, phoneData)
            return HandleRequest.send(result)

        }else{ //aztualizar sin imagen
            return HandleRequest.send(userService.update(id, user))
        }
    }
}