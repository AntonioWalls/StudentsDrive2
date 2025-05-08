package com.antoniowalls.indriverstudents.domain.repository

import com.antoniowalls.indriverstudents.domain.model.User
import java.io.File

interface UserRepository {
    suspend fun update(id: String, user: User, file: File?)
}