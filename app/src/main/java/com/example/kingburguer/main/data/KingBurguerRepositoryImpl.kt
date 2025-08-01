package com.example.kingburguer.main.data

import com.example.kingburguer.common.apiCall
import com.example.kingburguer.core.api.KingBurguerService
import com.example.kingburguer.core.data.ApiResult
import com.example.kingburguer.core.data.KingBurguerLocalStorage
import com.example.kingburguer.home.data.FeedResponse
import com.example.kingburguer.home.data.HighlightProductResponse
import com.example.kingburguer.product.data.CouponResponse
import com.example.kingburguer.product.data.ProductDetailsResponse
import com.example.kingburguer.profile.data.ProfileResponse

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


}