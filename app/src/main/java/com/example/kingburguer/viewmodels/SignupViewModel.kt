package com.example.kingburguer.viewmodels

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kingburguer.composes.signup.FieldState
import com.example.kingburguer.composes.signup.FormState
import com.example.kingburguer.composes.signup.SignupUiState
import com.example.kingburguer.validations.mask
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    var formState by mutableStateOf(FormState())

    fun updateEmail(email: String) {

        if (email.isBlank()) {
            formState = formState.copy(
                email = FieldState(
                    field = email,
                    error = "Campo e-mail não pode ser vazio"
                )
            )
            return
        }

        if (!isEmailValid(email)) {
            formState = formState.copy(
                email = FieldState(
                    field = email,
                    error = "E-mail inválido. Verifique o campo novamente."
                )
            )
            return

        }

        formState = formState.copy(email = FieldState(field = email, error = null))
    }

    fun updateName(name: String) {

        if (name.isBlank()) {
            formState = formState.copy(
                name = FieldState(
                    field = name,
                    error = "Campo nome não pode ser vazio"
                )
            )
            return
        }

        if (name.length < 3) {
            formState = formState.copy(
                name = FieldState(
                    field = name,
                    error = "Nome deve ter 3 letras ou mais"
                )
            )
            return

        }

        formState = formState.copy(name = FieldState(field = name, error = null))
    }

    fun updatePassword(password: String) {

        if (password.isBlank()) {
            formState = formState.copy(
                password = FieldState(
                    field = password,
                    error = "Campo senha não pode ser vazio"
                )
            )
            return
        }

        if (password.length < 8) {
            formState = formState.copy(
                password = FieldState(
                    field = password,
                    error = "Senha deve ter 8 letras ou mais"
                )
            )
            return
        }

        formState = formState.copy(password = FieldState(field = password, error = null))
    }

    fun updateConfirm(password: String) {

        if (password.isBlank()) {
            formState = formState.copy(
                confirmPassword = FieldState(
                    field = password,
                    error = "Campo confirmar senha não pode ser vazio"
                )
            )
            return
        }

        if (password != formState.password.field) {
            formState = formState.copy(
                confirmPassword = FieldState(
                    field = password,
                    error = "O confirmar senha devem ser iguail à senha"
                )
            )
            return
        }

        formState = formState.copy(confirmPassword = FieldState(field = password, error = null))
    }

    fun updateDocument(document: String){

        val pattern = "###.###.###-##"
        val currentDocument = formState.document.field
        val result = mask(pattern, currentDocument,document)

        if (result.isBlank()) {
            formState = formState.copy(
                document = FieldState(
                    field = result,
                    error = "Campo CPF não pode ser vazio"
                )
            )
            return
        }

        if (result.length != pattern.length) {
            formState = formState.copy(
                document = FieldState(
                    field = result,
                    error = "CPF inválido"
                )
            )
            return
        }

        formState = formState.copy(
            document = FieldState(field = result, error = null)
        )

    }

    fun send() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            delay(3000)

            _uiState.update {
                it.copy(isLoading = false, goToHome = true)
            }
        }

    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun reset() {
        _uiState.update {
            SignupUiState()
        }
    }
}