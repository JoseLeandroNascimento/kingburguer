package com.example.kingburguer.product.presentation

import com.example.kingburguer.product.data.CouponResponse
import com.example.kingburguer.product.data.ProductDetailsResponse

data class ProductUiState(
    val isLoading: Boolean = false,
    val productDetails: ProductDetailsResponse? = null,
    val coupon: CouponResponse? = null,
    val error: String? = null
)
