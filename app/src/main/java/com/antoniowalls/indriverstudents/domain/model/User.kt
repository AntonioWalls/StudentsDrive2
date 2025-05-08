package com.antoniowalls.indriverstudents.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable
//esta cosa fungiría como un DTO del backend (No uno como tal pero parecido)
data class User (
    @SerializedName("id") val id: Long? = null,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("email") val email: String?= null,
    @SerializedName("phone") val phone: String,
    @SerializedName("image") val image: String? = null,
    @SerializedName("notification_token") val notificationToken: Any? = null,
    @SerializedName("roles") val roles: List<Role>? = null,
    @SerializedName("password") val password: String? =null,

    ) : Serializable{
    fun toJson(): String = Gson().toJson(User(
        id = id,
        name = name,
        lastname = lastname,
        email = email,
        phone = phone
    ))

    companion object {
        fun fromJson(data: String): User = Gson().fromJson(data, User::class.java)
    }

}