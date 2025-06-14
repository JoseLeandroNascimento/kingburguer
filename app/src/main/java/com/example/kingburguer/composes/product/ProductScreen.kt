package com.example.kingburguer.composes.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kingburguer.ui.theme.KingburguerTheme
import com.example.kingburguer.viewmodels.ProductViewModel

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel(factory = ProductViewModel.factory)
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        Text(text = "Produto ${viewModel.productId}")

    }

}

@Preview(showBackground = true)
@Composable
private fun ProductScreenLightPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        ProductScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductScreenDarkPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        ProductScreen()
    }
}