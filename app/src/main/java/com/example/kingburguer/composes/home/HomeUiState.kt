package com.example.kingburguer.composes.home

import com.example.kingburguer.data.CategoryResponse

data class HomeUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoryResponse> = emptyList(),
    val error: String? = null
)
