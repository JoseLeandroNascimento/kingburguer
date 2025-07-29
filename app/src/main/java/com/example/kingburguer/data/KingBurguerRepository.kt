package com.example.kingburguer.data

import com.example.kingburguer.api.KingBurguerService
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response

class KingBurguerRepository(
    private val service: KingBurguerService,
    private val localStorage: KingBurguerLocalStorage
) {


    suspend fun createCoupon(productId: Int): ApiResult<CouponResponse> {
        val userCredentials = localStorage.fetchInitialUserCredential()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"

        return apiCall {
            service.createCoupon(token, productId)
        }
    }

    suspend fun fetchHighlight(): ApiResult<HighlightProductResponse> {

        val userCredentials = localStorage.fetchInitialUserCredential()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"

        return apiCall {
            service.fetchHighlight(token)
        }
    }


    suspend fun fetchFeed(): ApiResult<FeedResponse> {
        val userCredentials = localStorage.fetchInitialUserCredential()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"

        return apiCall {
            service.fetchFeed(token)
        }
    }

    suspend fun fetchMe(): ApiResult<ProfileResponse> {
        val userCredentials = localStorage.fetchInitialUserCredential()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"

        return apiCall {
            service.fetchMe(token)
        }
    }

    suspend fun fetchProductById(productId: Int): ApiResult<ProductDetailsResponse> {

        val userCredentials = localStorage.fetchInitialUserCredential()
        val token = "${userCredentials.tokenType} ${userCredentials.accessToken}"

        return apiCall {
            service.fetchProductById(token, productId)
        }
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