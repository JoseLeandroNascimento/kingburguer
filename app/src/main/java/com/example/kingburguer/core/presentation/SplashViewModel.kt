package com.example.kingburguer.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kingburguer.core.domain.CheckSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkSessionUseCase: CheckSessionUseCase
) : ViewModel() {

    private val _hasSessionState = MutableStateFlow<Boolean?>(null)
    val hasSession: StateFlow<Boolean?> = _hasSessionState

    init {
        checkAuthentication()
    }

    private fun checkAuthentication() {
        checkSessionUseCase.invoke().onEach { authenticated ->
            _hasSessionState.value = authenticated
        }.launchIn(viewModelScope)
    }

}