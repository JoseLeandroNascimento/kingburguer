package com.example.kingburguer.product.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.kingburguer.R
import com.example.kingburguer.common.currency
import com.example.kingburguer.composes.components.KingAlert
import com.example.kingburguer.composes.components.KingButton
import com.example.kingburguer.product.data.ProductDetailsResponse
import com.example.kingburguer.ui.theme.KingburguerTheme


@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel(),
    onCouponGenerated: () -> Unit
) {

    val state = viewModel.uiState.collectAsState().value

    ProductScreen(
        modifier = modifier,
        state = state,
        couponClicked = {
            viewModel.createCoupon()
        },
        onCouponGenerated = {
            viewModel.reset()
            onCouponGenerated()
        }
    )

}

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    state: ProductUiState,
    couponClicked: () -> Unit,
    onCouponGenerated: () -> Unit
) {

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        when {
            state.isLoading -> {

                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(text = state.error, color = MaterialTheme.colorScheme.primary)
            }

            else -> {
                state.productDetails?.let {
                    ProductScreen(modifier, state.productDetails, couponClicked = couponClicked)
                }

                state.coupon?.let {
                    KingAlert(
                        onDismissRequest = {},
                        onConfirmation = onCouponGenerated,
                        dialogTitle = stringResource(id = R.string.app_name),
                        dialogText = stringResource(
                            id = R.string.coupon_generated,
                            state.coupon.coupon
                        ),
                        icon = Icons.Filled.Info
                    )

                }
            }

        }
    }
}

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    productDetails: ProductDetailsResponse,
    couponClicked: () -> Unit
) {

    val scrollState = rememberScrollState()

    Surface(
        modifier = modifier.fillMaxSize()
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .verticalScroll(state = scrollState)
            ) {

                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    model = productDetails.pictureUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = productDetails.name,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp),
                        text = productDetails.price.currency(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface,
                        style = MaterialTheme.typography.titleMedium
                    )

                }
                Text(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 56.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    text = productDetails.description
                )

            }

            KingButton(
                text = stringResource(id = R.string.get_coupon),
                modifier = Modifier.padding(horizontal = 24.dp),
                onClick = couponClicked
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun ProductScreenLightPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        ProductScreen(state = ProductUiState(), couponClicked = {}, onCouponGenerated = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductScreenDarkPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        ProductScreen(state = ProductUiState(), couponClicked = {}, onCouponGenerated = {})
    }
}