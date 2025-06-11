package com.example.kingburguer.data

import com.example.kingburguer.api.KingBurguerService
import com.google.gson.Gson

class KingBurguerRepository(
    private val service: KingBurguerService
) {

    suspend fun postUser(userRequest: UserRequest): UserCreateResponse {

        val response = service.postUser(userRequest)

        try {
            if (!response.isSuccessful) {

                val errorData = response.errorBody()?.string()?.let {
                    if (response.code() == 401) {
                        Gson().fromJson(it, UserCreateResponse.ErrorAuth::class.java)
                    } else {
                        Gson().fromJson(it, UserCreateResponse.Error::class.java)
                    }
                }

                return errorData ?: UserCreateResponse.Error("Internal server error")
            }

            val data = response.body()?.string()?.let {
                Gson().fromJson(it, UserCreateResponse.Success::class.java)
            }

            return data ?: UserCreateResponse.Error("Unexpected response success")
        } catch (e: Exception) {

            return UserCreateResponse.Error(e.message ?: "Unexpected exceptions")
        }
    }

}