package com.example.kingburguer.auth.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kingburguer.auth.data.UserCreateRequest
import com.example.kingburguer.auth.domain.AuthRepository
import com.example.kingburguer.data.ApiResult
import com.example.kingburguer.validations.BirthdateValidator
import com.example.kingburguer.validations.ConfirmPasswordValidator
import com.example.kingburguer.validations.DocumentValidator
import com.example.kingburguer.validations.EmailValidator
import com.example.kingburguer.validations.NameValidator
import com.example.kingburguer.validations.PasswordValidator
import com.example.kingburguer.validations.mask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

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

    fun updateName(name: String) {
        val errorMessage = NameValidator.Companion.validate(name)
        formState = formState.copy(
            name = FieldState(
                field = name,
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

        errorMessage =
            ConfirmPasswordValidator.Companion.validate(formState.confirmPassword.field, password)
        formState = formState.copy(
            confirmPassword = FieldState(
                field = formState.confirmPassword.field,
                error = errorMessage,
                isValid = errorMessage == null
            )
        )
        updateButton()

    }

    fun updateConfirm(password: String) {

        var errorMessage =
            ConfirmPasswordValidator.Companion.validate(password, formState.password.field)

        formState =
            formState.copy(
                confirmPassword = FieldState(
                    field = password,
                    error = errorMessage,
                    isValid = errorMessage == null
                )
            )

        errorMessage = PasswordValidator.Companion.validate(formState.password.field)
        formState = formState.copy(
            password = FieldState(
                field = formState.password.field,
                error = errorMessage,
                isValid = errorMessage == null
            )
        )

        updateButton()

    }

    fun updateDocument(document: String) {

        val currentDocument = formState.document.field
        val pattern = "###.###.###-##"
        val result = mask(pattern, currentDocument, document)
        val errorMessage = DocumentValidator.Companion.validate(result, pattern)

        formState = formState.copy(
            document = FieldState(
                field = result,
                error = errorMessage,
                isValid = errorMessage == null
            )
        )
        updateButton()


    }

    fun updateBirthdate(value: String) {

        val pattern = "##/##/####"
        val currentBirthdate = formState.birthdate.field
        val result = mask(pattern, currentBirthdate, value)

        val errorMessage = BirthdateValidator.Companion.validate(result, pattern)

        formState = formState.copy(
            birthdate = FieldState(
                field = result,
                error = errorMessage,
                isValid = errorMessage == null
            )
        )
        updateButton()

    }


    private fun updateButton() {
        val formIsValid = with(formState) {
            email.isValid &&
                    name.isValid &&
                    password.isValid &&
                    confirmPassword.isValid &&
                    document.isValid &&
                    birthdate.isValid
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

                val date =
                    java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
                        .parse(birthdate.field)
                val dateFormat = java.text.SimpleDateFormat(
                    "yyyy-MM-dd",
                    java.util.Locale.getDefault()
                ).format(date!!)

                val documentFormatted = document.field.filter { it.isDigit() }

                val userCreateRequest = UserCreateRequest(
                    name = name.field,
                    email = email.field,
                    password = password.field,
                    document = documentFormatted,
                    birthday = dateFormat
                )


                val response = repository.postUser(userCreateRequest)


                when (response) {
                    is ApiResult.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false, goToLogin = true)
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
            SignupUiState()
        }
    }

}