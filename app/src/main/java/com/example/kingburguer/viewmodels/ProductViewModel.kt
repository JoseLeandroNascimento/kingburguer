package com.example.kingburguer.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class ProductViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val productId: Int = savedStateHandle["productId"] ?: 0

    companion object {

        val factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                ProductViewModel(savedStateHandle)
            }
        }
    }
}