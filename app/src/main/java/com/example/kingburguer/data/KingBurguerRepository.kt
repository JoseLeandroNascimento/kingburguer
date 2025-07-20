package com.example.kingburguer.data

import com.example.kingburguer.api.KingBurguerService
import com.google.gson.Gson
import retrofit2.Response

class KingBurguerRepository(
    private val service: KingBurguerService,
    private val localStorage: KingBurguerLocalStorage
) {

    suspend fun fetchInitialCredentials() = localStorage.fetchInitialUserCredential()

    suspend fun login(loginRequest: LoginRequest, keepLogged: Boolean): ApiResult<LoginResponse> {

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


    suspend fun refreshToken(request: RefreshTokenRequest): ApiResult<LoginResponse> {

        try {

            val userCredentials = localStorage.fetchInitialUserCredential()
            val response = apiCall {
                service.refreshToken(
                    request,
                    "${userCredentials.tokenType} ${userCredentials.accessToken}"
                )
            }

            if (response is ApiResult.Success) {

                updateCredentials((response.data))
            }

            return response

        } catch (e: Exception) {

            return ApiResult.Error(e.message ?: "Unexpected exceptions")
        }
    }


    suspend fun postUser(userRequest: UserRequest): ApiResult<UserCreateResponse> {

        val response = apiCall {
            service.postUser(userRequest)
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
                        val errorAuth = Gson().fromJson(it, ErrorAuth::class.java)
                        ApiResult.Error(errorAuth.detail.message)
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