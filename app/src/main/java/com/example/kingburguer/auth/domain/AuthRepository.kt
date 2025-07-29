package com.example.kingburguer.auth.domain

import com.example.kingburguer.auth.data.LoginRequest
import com.example.kingburguer.auth.data.LoginResponse
import com.example.kingburguer.auth.data.UserCreateRequest
import com.example.kingburguer.auth.data.UserCreateResponse
import com.example.kingburguer.data.ApiResult

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest, keepLogged: Boolean): ApiResult<LoginResponse>

    suspend fun postUser(userCreateRequest: UserCreateRequest): ApiResult<UserCreateResponse>
}