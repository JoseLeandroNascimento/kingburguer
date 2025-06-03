package com.example.kingburguer.composes.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kingburguer.R
import com.example.kingburguer.ui.theme.KingburguerTheme

@Composable
fun KingAlert(
    modifier: Modifier = Modifier,
    dialogTitle: String,
    dialogText: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    icon: ImageVector,

    ) {
    AlertDialog(
        icon = {
            Icon(imageVector = icon, contentDescription = stringResource(id = R.string.alert_icon))
        },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        }
    )
}

@Preview
@Composable
private fun KingAlertLightPreview() {
    KingburguerTheme(dynamicColor = false) {
        KingAlert(
            dialogTitle = "Dialog",
            dialogText = "Texto da dialog",
            onDismissRequest = {},
            onConfirmation = {},
            icon = Icons.Filled.Error
        )
    }
}

@Preview
@Composable
private fun KingAlertDarkPreview() {
    KingburguerTheme(dynamicColor = false, darkTheme = true) {
        KingAlert(
            dialogTitle = "Dialog",
            dialogText = "Texto da dialog",
            onDismissRequest = {},
            onConfirmation = {},
            icon = Icons.Filled.Error
        )
    }
}