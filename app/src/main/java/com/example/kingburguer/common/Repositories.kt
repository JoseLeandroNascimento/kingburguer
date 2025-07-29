package com.example.kingburguer.common

import com.example.kingburguer.data.ApiResult
import com.example.kingburguer.data.Error
import com.example.kingburguer.data.ErrorAuth
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response

suspend fun <T> apiCall(call: suspend () -> Response<T>): ApiResult<T> {
    try {

        val response = call()

        if (!response.isSuccessful) {
            val errorData = response.errorBody()?.string()?.let {
                if (response.code() == 401) {
                    try {
                        val errorAuth = Gson().fromJson(it, ErrorAuth::class.java)
                        ApiResult.Error(errorAuth.detail.message)

                    } catch (e: JsonSyntaxException) {
                        val error = Gson().fromJson(it, Error::class.java)
                        ApiResult.Error(error.detail)
                    }
                } else {
                    Gson().fromJson(it, ApiResult.Error::class.java)

                }
            }
            return errorData ?: ApiResult.Error("Internal server error")
        }

        val data = response.body()

        if (data == null) return ApiResult.Error("Unexpected response success")

        return ApiResult.Success(data)

    } catch (e: Exception) {

        return ApiResult.Error(e.message ?: "Unexpected exceptions")
    }
}