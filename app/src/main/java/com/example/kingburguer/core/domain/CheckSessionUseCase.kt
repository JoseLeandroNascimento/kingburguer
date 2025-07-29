package com.example.kingburguer.core.domain

import com.example.kingburguer.auth.data.RefreshTokenRequest
import com.example.kingburguer.auth.domain.AuthRepository
import com.example.kingburguer.data.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckSessionUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(): Flow<Boolean> = flow {
        with(repository.fetchInitialCredentials()) {

            val res = when {
                accessToken.isEmpty() -> false
                System.currentTimeMillis() < expiresTimestamp -> true
                else -> {
                    val response = repository.refreshToken(RefreshTokenRequest(refreshToken))
                    when (response) {
                        is ApiResult.Success -> true
                        else -> false
                    }
                }
            }

            emit(res)
        }
    }
}