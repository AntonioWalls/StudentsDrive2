package com.antoniowalls.indriverstudents.data.util

import android.util.Log
import com.antoniowalls.indriverstudents.domain.model.ErrorResponse
import com.antoniowalls.indriverstudents.domain.util.ErrorHelper
import com.antoniowalls.indriverstudents.domain.util.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object HandleRequest {
    fun <T> send(result: Response<T>, tag: String= "HandleRequest"): Resource<T>{
        return try{
            if (result.isSuccessful){
                Log.d(tag, "Data Success ${result.body()}")
                Resource.Success(result.body()!!) //el !! sirve para los nulos
            } else{
                Log.d(tag, "Error en la petición")
                val errorResponse : ErrorResponse? = ErrorHelper.handleError(result.errorBody())
                Resource.Failure(errorResponse?.message?:"Error desconocido" )
            }

        } catch (e: HttpException){
            Log.d("${tag}:HttpException", "Message: ${e}")
            Log.d("${tag}:HttpException", "Message Cause: ${e.cause}")
            e.printStackTrace()
            Resource.Failure(e.message ?: "Error desconocido" )
        }
        catch (e: IOException){
            Log.d("${tag}:IOException", "Message: ${e}")
            Log.d("${tag}:IOException", "Message Cause: ${e.cause}")
            e.printStackTrace()
            Resource.Failure("Verifica tu conexión a internet")
        }catch (e: Exception){
            Log.d(tag, "Message: ${e}")
            Log.d(tag, "Message Cause: ${e.cause}")
            e.printStackTrace()
            Resource.Failure(e.message ?: "Error desconocido" )
        }
    }
}