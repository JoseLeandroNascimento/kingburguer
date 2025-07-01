package com.example.kingburguer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.data.KingBurguerLocalStorage
import com.example.kingburguer.data.KingBurguerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: KingBurguerRepository
) : ViewModel() {

    private val _hasSessionState = MutableStateFlow<Boolean?>(null)
    val hasSession: StateFlow<Boolean?> = _hasSessionState

    init {

        viewModelScope.launch {
            with(repository.fetchInitialCredentials()) {
                _hasSessionState.value =
                    (accessToken.isNotBlank() && System.currentTimeMillis() < expiresTimestamp)
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

                SplashViewModel(repository)

            }
        }
    }

}