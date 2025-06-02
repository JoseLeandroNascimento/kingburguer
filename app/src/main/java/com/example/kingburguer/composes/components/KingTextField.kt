package com.example.kingburguer.composes.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.R

@Composable
fun KingTextField(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    obfuscate: Boolean = false,
    keyBoardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),

        label = {
            Text(text = stringResource(id = label))
        },
        placeholder = {
            Text(text = stringResource(id = placeholder))
        },
        maxLines = 1,
        value = value,
        visualTransformation = if (obfuscate) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = trailingIcon,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoardType,
            imeAction = imeAction
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun KingTextFieldPreview() {

    var passwordHidden by remember {
        mutableStateOf(true)
    }

    KingTextField(
        value = "todo",
        label = R.string.password,
        placeholder = R.string.hint_password,
        keyBoardType = KeyboardType.Password,
        imeAction = ImeAction.Done,
        obfuscate = passwordHidden,
        modifier = Modifier.padding(horizontal = 20.dp),
        trailingIcon = {
            IconButton(
                onClick = {
                    passwordHidden = !passwordHidden
                }
            ) {
                val image = if (passwordHidden) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.VisibilityOff
                }

                Icon(imageVector = image, contentDescription = "")
            }
        }
    ) {

    }
}