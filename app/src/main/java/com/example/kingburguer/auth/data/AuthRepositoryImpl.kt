package com.example.kingburguer.auth.data

import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.auth.domain.AuthRepository
import com.example.kingburguer.common.apiCall
import com.example.kingburguer.data.ApiResult
import com.example.kingburguer.data.KingBurguerLocalStorage
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

    override suspend fun fetchInitialCredentials() = localStorage.fetchInitialUserCredential()

    override suspend fun refreshToken(request: RefreshTokenRequest): ApiResult<LoginResponse> {

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

    private suspend fun updateCredentials(data: LoginResponse) {
        val newUserCredentials = UserCredentials(
            data.accessToken,
            data.refreshToken,
            data.expiresSeconds.toLong(),
            data.tokenType
        )
        localStorage.updateUserCredential(newUserCredentials)
    }


}