package com.example.kingburguer.home.presentation

import com.example.kingburguer.home.data.CategoryResponse
import com.example.kingburguer.home.data.HighlightProductResponse

data class HomeUiState(
    val categoryUiState: CategoryUiState = CategoryUiState(),
    val highlightUiState: HighlightUiState = HighlightUiState()
)

data class CategoryUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoryResponse> = emptyList(),
    val error: String? = null
)

data class HighlightUiState(
    val isLoading: Boolean = false,
    val product: HighlightProductResponse? = null,
    val error: String? = null
)
