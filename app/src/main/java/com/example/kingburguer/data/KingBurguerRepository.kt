package com.example.kingburguer.data

import com.example.kingburguer.api.KingBurguerService
import com.google.gson.Gson

class KingBurguerRepository(
    private val service: KingBurguerService,
    private val localStorage: KingBurguerLocalStorage
) {

    suspend fun fetchInitialCredentials() = localStorage.fetchInitialUserCredential()

    suspend fun login(loginRequest: LoginRequest, keepLogged: Boolean): LoginResponse {

        try {

            val response = service.login(loginRequest)

            if (!response.isSuccessful) {
                val errorData = response.errorBody()?.string()?.let {
                    Gson().fromJson(it, LoginResponse.ErrorAuth::class.java)
                }
                return errorData ?: LoginResponse.Error("Internal server error")
            }

            val data = response.body()?.string()?.let {
                Gson().fromJson(it, LoginResponse.Success::class.java)
            }

            if (data == null) return LoginResponse.Error("Unexpected response success")

            if (keepLogged) {

                val userCredentials = UserCredentials(
                    data.accessToken,
                    data.refreshToken,
                    data.expiresSeconds.toLong(),
                    data.tokenType
                )

                localStorage.updateUserCredential(userCredentials)
            }


            return data

        } catch (e: Exception) {

            return LoginResponse.Error(e.message ?: "Unexpected exceptions")
        }
    }

    suspend fun refreshToken(request: RefreshTokenRequest): LoginResponse {

        try {

            val userCredentials = localStorage.fetchInitialUserCredential()
            val response = service.refreshToken(
                request,
                "${userCredentials.tokenType} ${userCredentials.accessToken}"
            )

            if (!response.isSuccessful) {
                val errorData = response.errorBody()?.string()?.let {
                    Gson().fromJson(it, LoginResponse.ErrorAuth::class.java)
                }
                return errorData ?: LoginResponse.Error("Internal server error")
            }

            val data = response.body()?.string()?.let {
                Gson().fromJson(it, LoginResponse.Success::class.java)
            }

            if (data == null) return LoginResponse.Error("Unexpected response success")


            val newUserCredentials = UserCredentials(
                data.accessToken,
                data.refreshToken,
                data.expiresSeconds.toLong(),
                data.tokenType
            )

            localStorage.updateUserCredential(newUserCredentials)

            return data

        } catch (e: Exception) {

            return LoginResponse.Error(e.message ?: "Unexpected exceptions")
        }
    }

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