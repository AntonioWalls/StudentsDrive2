package com.antoniowalls.indriverstudents.domain.model

import com.squareup.moshi.JsonClass

//acá lo que estamos haciendo es hacer un modelo para manejar los errores de nuestra API
//Respetando siempre los nombres de las variables a como vienen en la API
@JsonClass(generateAdapter = true)
data class ErrorResponse (
    val statusCode: Int = 500,
    val message: String = ""
)
