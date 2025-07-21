package com.example.kingburguer.api

import com.example.kingburguer.data.FeedResponse
import com.example.kingburguer.data.LoginRequest
import com.example.kingburguer.data.LoginResponse
import com.example.kingburguer.data.RefreshTokenRequest
import com.example.kingburguer.data.UserCreateResponse
import com.example.kingburguer.data.UserRequest
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT


interface KingBurguerService {

    @POST("users")
    suspend fun postUser(
        @Body userRequest: UserRequest,
        @Header("x-secret-key") secretKey: String = "9974958d-ec79-4167-b50c-2af5a9012d88"
    ): Response<UserCreateResponse>

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
        @Header("x-secret-key") secretKey: String = "9974958d-ec79-4167-b50c-2af5a9012d88"
    ): Response<LoginResponse>

    @PUT("auth/refresh-token")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest,
        @Header("Authorization") token: String,
    ): Response<LoginResponse>

    @GET("feed")
    suspend fun fetchFeed(@Header("Authorization") token: String): Response<FeedResponse>

    companion object {

        private const val BASE_URL = "https://hades.tiagoaguiar.co/kingburguer/"

        fun create(): KingBurguerService {

            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val clientOk = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientOk)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(KingBurguerService::class.java)
        }

    }

}