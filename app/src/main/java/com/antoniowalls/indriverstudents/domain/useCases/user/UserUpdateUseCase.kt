package com.antoniowalls.indriverstudents.domain.useCases.user

import com.antoniowalls.indriverstudents.domain.model.User
import com.antoniowalls.indriverstudents.domain.repository.UserRepository
import java.io.File

class UserUpdateUseCase(private val repository: UserRepository)  {
    suspend operator fun invoke(id: String, user: User, file: File?) = repository.update(id, user, file)
}