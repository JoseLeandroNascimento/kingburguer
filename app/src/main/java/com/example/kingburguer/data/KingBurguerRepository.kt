package com.example.kingburguer.data

import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.common.apiCall
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