package com.example.kingburguer.auth.data

import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.auth.domain.AuthRepository
import com.example.kingburguer.data.ApiResult
import com.example.kingburguer.data.Error
import com.example.kingburguer.data.ErrorAuth
import com.example.kingburguer.data.KingBurguerLocalStorage
import com.example.kingburguer.data.UserCredentials
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: KingBurguerService,
    private val localStorage: KingBurguerLocalStorage
) : AuthRepository {

    override suspend fun login(
        loginRequest: LoginRequest,
        keepLogged: Boolean
    ): ApiResult<LoginResponse> {

        val result = apiCall {
            service.login(loginRequest)
        }

        if (result is ApiResult.Success<LoginResponse>) {

            if (keepLogged) {
                updateCredentials((result.data))
            }
        }
        return result

    }

    override suspend fun postUser(userCreateRequest: UserCreateRequest): ApiResult<UserCreateResponse> {

        val response = apiCall {
            service.postUser(userCreateRequest)
        }

        return response

    }

    private suspend fun updateCredentials(data: LoginResponse) {
        val newUserCredentials = UserCredentials(
            data.accessToken,
            data.refreshToken,
            data.expiresSeconds.toLong(),
            data.tokenType
        )
        localStorage.updateUserCredential(newUserCredentials)
    }


    private suspend fun <T> apiCall(call: suspend () -> Response<T>): ApiResult<T> {
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
}