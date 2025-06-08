package com.example.kingburguer.validations

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

class ResourceString(@StringRes val input: Int) : TextString {

    override val value: String
        @Composable
        get() = stringResource(input)


}