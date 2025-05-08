package com.antoniowalls.indriverstudents.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

//esta cosa fungiría como un DTO del backend (No uno como tal pero parecido)
data class User (
    @SerializedName("id") val id: Long? = null,
    @SerializedName("name") var name: String,
    @SerializedName("lastname") var lastname: String,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") var phone: String,
    @SerializedName("image") var image: String? = null,
    @SerializedName("notification_token") val notificationToken: String? = null,
    @SerializedName("roles") val roles: List<Role>? = null,
    @SerializedName("password") val password: String? = null
) : Serializable {
    fun toJson(): String = Gson().toJson(User(
        id = id,
        name = name,
        lastname = lastname,
        email = email,
        phone = phone,
        image = if(!image.isNullOrBlank()) URLEncoder.encode(image, StandardCharsets.UTF_8.toString()) else null
    ))

    companion object {
        fun fromJson(data: String): User = Gson().fromJson(data, User::class.java)
    }
}