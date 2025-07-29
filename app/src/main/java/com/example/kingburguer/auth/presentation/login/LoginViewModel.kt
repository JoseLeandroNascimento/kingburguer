package com.example.kingburguer.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kingburguer.auth.data.LoginRequest
import com.example.kingburguer.auth.domain.AuthRepository
import com.example.kingburguer.auth.presentation.signup.FieldState
import com.example.kingburguer.data.ApiResult
import com.example.kingburguer.validations.EmailValidator
import com.example.kingburguer.validations.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    var formState by mutableStateOf(FormState())


    fun updateEmail(email: String) {

        val errorMessage = EmailValidator.Companion.validate(email)

        formState = formState.copy(
            email = FieldState(
                field = email,
                error = errorMessage,
                isValid = errorMessage == null
            )
        )

        updateButton()
    }

    fun updatePassword(password: String) {

        var errorMessage = PasswordValidator.Companion.validate(password)
        formState = formState.copy(
            password = FieldState(
                field = password,
                error = errorMessage,
                isValid = errorMessage == null
            )
        )

        updateButton()

    }

    fun updateRememberMe(rememberMe: Boolean) {
        formState = formState.copy(
            rememberMe = rememberMe
        )
    }

    private fun updateButton() {
        val formIsValid = with(formState) {
            email.isValid && password.isValid
        }

        formState = formState.copy(
            formIsValid = formIsValid
        )
    }

    fun send() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {


            with(formState) {

                val loginRequest = LoginRequest(
                    username = email.field,
                    password = password.field,
                )

                val response = repository.login(loginRequest, rememberMe)

                when (response) {
                    is ApiResult.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false, goToHome = true)
                        }
                    }

                    is ApiResult.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, error = response.message)
                        }
                    }
                }
            }

        }

    }


    fun reset() {
        _uiState.update {
            LoginUiState()
        }
    }


}