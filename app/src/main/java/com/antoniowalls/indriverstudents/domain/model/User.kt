package com.antoniowalls.indriverstudents.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
//esta cosa fungiría como un DTO del backend (No uno como tal pero parecido)
data class User (
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("image") val image: String,
    @SerializedName("notification_token") val notificationToken: Any? = null,
    @SerializedName("roles") val roles: List<Role>
) : Serializable