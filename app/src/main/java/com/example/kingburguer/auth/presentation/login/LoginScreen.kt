package com.example.kingburguer.auth.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kingburguer.R
import com.example.kingburguer.composes.components.KingAlert
import com.example.kingburguer.composes.components.KingButton
import com.example.kingburguer.composes.components.KingTextField
import com.example.kingburguer.composes.components.KingTextTitle
import com.example.kingburguer.ui.theme.KingburguerTheme
import com.example.kingburguer.auth.presentation.login.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel(),
    onNavigateToHome: () -> Unit,
    onSignup: () -> Unit
) {

    Surface(modifier = modifier.fillMaxSize()) {

        val scrollState = rememberScrollState()
        var passwordHidden by remember {
            mutableStateOf(true)
        }
        val uiState by loginViewModel.uiState.collectAsState()

        Column {

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 40.dp)
                    .verticalScroll(state = scrollState)
            ) {

                LaunchedEffect(uiState.goToHome) {
                    if (uiState.goToHome) {
                        onNavigateToHome()
                        loginViewModel.reset()
                    }
                }

                uiState.error?.let { error ->
                    KingAlert(
                        dialogTitle = stringResource(id = R.string.app_name),
                        dialogText = error,
                        onDismissRequest = {

                        },
                        onConfirmation = {
                            loginViewModel.reset()
                        },
                        icon = Icons.Filled.Info
                    )
                }


                KingTextTitle(text = stringResource(id = R.string.login))

                KingTextField(
                    value = loginViewModel.formState.email.field,
                    error = loginViewModel.formState.email.error?.value,
                    label = R.string.email,
                    placeholder = R.string.hint_email,
                    keyBoardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    modifier = Modifier
                ) {

                    loginViewModel.updateEmail(it)

                }

                KingTextField(
                    value = loginViewModel.formState.password.field,
                    error = loginViewModel.formState.password.error?.value,
                    label = R.string.password,
                    placeholder = R.string.hint_password,
                    keyBoardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    obfuscate = passwordHidden,
                    modifier = Modifier,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordHidden = !passwordHidden
                            }
                        ) {
                            val image = if (passwordHidden) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            }

                            Icon(imageVector = image, contentDescription = "")
                        }
                    }
                ) {

                    loginViewModel.updatePassword(it)

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = loginViewModel.formState.rememberMe, onCheckedChange = {
                        loginViewModel.updateRememberMe(it)
                    })
                    Text(text = stringResource(id = R.string.remember_me))
                }

                KingButton(
                    enable = loginViewModel.formState.formIsValid,
                    loading = uiState.isLoading,
                    text = stringResource(R.string.send)
                ) {
                    loginViewModel.send()
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.hav_account))
                    TextButton(onClick = onSignup) {
                        Text(text = stringResource(id = R.string.sign_up))
                    }
                }

            }
            Image(
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter,
                painter = painterResource(id = R.drawable.cover3),
                contentDescription = ""
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenLightPreview() {
    KingburguerTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        LoginScreen(onSignup = {}, onNavigateToHome = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenDarkPreview() {
    KingburguerTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        LoginScreen(onSignup = {}, onNavigateToHome = {})
    }
}