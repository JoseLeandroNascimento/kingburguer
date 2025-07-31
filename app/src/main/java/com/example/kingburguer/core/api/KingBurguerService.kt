package com.example.kingburguer.api

import com.example.kingburguer.product.data.CouponResponse
import com.example.kingburguer.home.data.FeedResponse
import com.example.kingburguer.home.data.HighlightProductResponse
import com.example.kingburguer.auth.data.LoginRequest
import com.example.kingburguer.auth.data.LoginResponse
import com.example.kingburguer.product.data.ProductDetailsResponse
import com.example.kingburguer.profile.data.ProfileResponse
import com.example.kingburguer.auth.data.RefreshTokenRequest
import com.example.kingburguer.auth.data.UserCreateResponse
import com.example.kingburguer.auth.data.UserCreateRequest
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface KingBurguerService {

    @POST("users")
    suspend fun postUser(
        @Body userCreateRequest: UserCreateRequest,
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

    @GET("products/{id}")
    suspend fun fetchProductById(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Response<ProductDetailsResponse>

    @POST("products/{id}/coupon")
    suspend fun createCoupon(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Response<CouponResponse>

    @GET("highlight")
    suspend fun fetchHighlight(@Header("Authorization") token: String): Response<HighlightProductResponse>

    @GET("users/me")
    suspend fun fetchMe(@Header("Authorization") token: String): Response<ProfileResponse>

    companion object {

        const val BASE_URL = "https://hades.tiagoaguiar.co/kingburguer/"

        fun create(): KingBurguerService {

            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val clientOk = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientOk)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(KingBurguerService::class.java)
        }

    }

}