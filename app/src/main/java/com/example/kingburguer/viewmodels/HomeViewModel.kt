package com.example.kingburguer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.composes.home.HomeUiState
import com.example.kingburguer.data.ApiResult
import com.example.kingburguer.data.KingBurguerLocalStorage
import com.example.kingburguer.data.KingBurguerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: KingBurguerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        start()
    }

    fun start() {

        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val response = repository.fetchFeed()

            when (response) {

                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = response.message)
                    }
                }

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            categories = response.data.categories
                        )
                    }
                }
            }
        }
    }

    companion object {

        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]!!.applicationContext
                val service = KingBurguerService.create()
                val localStorage = KingBurguerLocalStorage(application)
                val repository = KingBurguerRepository(service, localStorage)
                HomeViewModel(repository)
            }
        }
    }
}