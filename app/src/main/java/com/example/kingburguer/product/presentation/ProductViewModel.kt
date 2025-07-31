package com.example.kingburguer.product.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kingburguer.data.ApiResult
import com.example.kingburguer.data.KingBurguerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: KingBurguerRepository
) : ViewModel() {

    val productId: Int = savedStateHandle["productId"] ?: 0

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        start()
    }

    private fun start() {

        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {

            val response = repository
                .fetchProductById(productId)

            when (response) {

                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = response.message)
                    }
                }

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, productDetails = response.data)
                    }
                }
            }
        }

    }

    fun reset() {
        _uiState.value = ProductUiState()
    }

    fun createCoupon() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {

            val response = repository
                .createCoupon(productId)

            when (response) {

                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = response.message)
                    }
                }

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, coupon = response.data)
                    }
                }
            }
        }
    }
}