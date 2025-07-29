package com.example.kingburguer.profile.presentation

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
class ProfileViewModel @Inject constructor(
    private val repository: KingBurguerRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        start()
    }

    private fun start() {

        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {

            val response = repository
                .fetchMe()

            when (response) {

                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = response.message)
                    }
                }

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, profile = response.data)
                    }
                }
            }
        }

    }
}