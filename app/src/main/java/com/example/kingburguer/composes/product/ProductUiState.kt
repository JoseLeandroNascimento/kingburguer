package com.example.kingburguer.composes.product

import com.example.kingburguer.data.ProductDetailsResponse

data class ProductUiState(
    val isLoading: Boolean = false,
    val productDetails: ProductDetailsResponse? = null,
    val error: String? = null
)
