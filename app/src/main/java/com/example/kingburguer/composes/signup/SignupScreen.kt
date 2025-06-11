package com.example.kingburguer.composes.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kingburguer.R
import com.example.kingburguer.composes.components.KingAlert
import com.example.kingburguer.composes.components.KingButton
import com.example.kingburguer.composes.components.KingTextField
import com.example.kingburguer.composes.components.KingTextTitle
import com.example.kingburguer.ui.theme.KingburguerTheme
import com.example.kingburguer.viewmodels.SignupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: SignupViewModel = viewModel(factory = SignupViewModel.factory),

    ) {

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.login)) },
                    navigationIcon = {
                        IconButton(onClick = onNavigationClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    )
                )
            }
        ) { contentPadding ->
            SignupContentScreen(
                viewModel = viewModel,
                onNavigateToLogin = onNavigateToLogin,
                modifier = Modifier.padding(top = contentPadding.calculateTopPadding())
            )
        }
    }

}

@Composable
fun SignupContentScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    viewModel: SignupViewModel
) {
    Surface(modifier = modifier.fillMaxSize()) {

        val scrollState = rememberScrollState()
        var passwordHidden by remember {
            mutableStateOf(true)
        }
        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(state = scrollState)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {


                if (uiState.goToLogin) {

                    KingAlert(
                        onDismissRequest = {},
                        onConfirmation = {
                            onNavigateToLogin()
                            viewModel.reset()
                        },
                        dialogTitle = stringResource(id = R.string.app_name),
                        dialogText = stringResource(id = R.string.user_created),
                        icon = Icons.Filled.Info
                    )
                }

                uiState.error?.let { error ->
                    KingAlert(
                        dialogTitle = stringResource(id = R.string.app_name),
                        dialogText = error,
                        onDismissRequest = {

                        },
                        onConfirmation = {
                            viewModel.reset()
                        },
                        icon = Icons.Filled.Info
                    )
                }


                KingTextTitle(text = stringResource(id = R.string.sign_up))

                KingTextField(
                    value = viewModel.formState.email.field,
                    error = viewModel.formState.email.error?.value,
                    label = R.string.email,
                    placeholder = R.string.hint_email,
                    keyBoardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    modifier = Modifier
                ) {
                    viewModel.updateEmail(it)
                }

                KingTextField(
                    value = viewModel.formState.name.field,
                    label = R.string.name,
                    error = viewModel.formState.name.error?.value,
                    placeholder = R.string.hint_name,
                    keyBoardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    modifier = Modifier
                ) {
                    viewModel.updateName(it)
                }

                KingTextField(
                    value = viewModel.formState.password.field,
                    label = R.string.password,
                    error = viewModel.formState.password.error?.value,
                    placeholder = R.string.hint_password,
                    keyBoardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
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

                    viewModel.updatePassword(it)

                }

                KingTextField(
                    value = viewModel.formState.confirmPassword.field,
                    error = viewModel.formState.confirmPassword.error?.value,
                    label = R.string.confirm_password,
                    placeholder = R.string.hint_confirm_password,
                    keyBoardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
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

                    viewModel.updateConfirm(it)

                }

                KingTextField(
                    value = TextFieldValue(
                        text = viewModel.formState.document.field,
                        selection = TextRange(viewModel.formState.document.field.length)
                    ),
                    error = viewModel.formState.document.error?.value,
                    label = R.string.documento,
                    placeholder = R.string.hint_documento,
                    keyBoardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                    modifier = Modifier
                ) {

                    viewModel.updateDocument(it.text)

                }

                KingTextField(
                    value = TextFieldValue(
                        text = viewModel.formState.birthdate.field,
                        selection = TextRange(index = viewModel.formState.birthdate.field.length)
                    ),
                    error = viewModel.formState.birthdate.error?.value,
                    label = R.string.birthdate,
                    placeholder = R.string.hint_birthdate,
                    keyBoardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                    modifier = Modifier
                ) {

                    viewModel.updateBirthdate(it.text)
                }

                KingButton(
                    enable = viewModel.formState.formIsValid,
                    loading = uiState.isLoading,
                    text = stringResource(R.string.sign_up)
                ) {
                    viewModel.send()
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

@Preview(showBackground = true)
@Composable
private fun SignupScreenLightPreview() {
    KingburguerTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        SignupScreen(onNavigationClick = {}, onNavigateToLogin = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun SignupScreenDarkPreview() {
    KingburguerTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        SignupScreen(onNavigationClick = {}, onNavigateToLogin = {})
    }
}