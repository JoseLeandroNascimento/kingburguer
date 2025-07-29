package com.example.kingburguer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kingburguer.data.KingBurguerLocalStorage
import com.example.kingburguer.auth.data.UserCredentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val localStorage: KingBurguerLocalStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            localStorage.updateUserCredential(UserCredentials())
            _uiState.value = true
        }
    }

    companion object{
        val factory = viewModelFactory{
            initializer {

                val application = this[APPLICATION_KEY]!!.applicationContext
                val localStorage = KingBurguerLocalStorage(application)
                MainViewModel(localStorage)
            }
        }
    }

}