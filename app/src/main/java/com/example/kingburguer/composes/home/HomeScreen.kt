package com.example.kingburguer.composes.home

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.kingburguer.R
import com.example.kingburguer.common.currency
import com.example.kingburguer.data.CategoryResponse
import com.example.kingburguer.data.HighlightProductResponse
import com.example.kingburguer.ui.theme.KingburguerTheme
import com.example.kingburguer.ui.theme.Orange600
import com.example.kingburguer.viewmodels.HomeViewModel
import java.util.Date

data class Product(
    val id: Int,
    val name: String = "",
    @DrawableRes val picture: Int = R.drawable.example,
    val price: Double = 19.9
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory),
    onProductClicked: (Int) -> Unit
) {

    val state = viewModel.uiState.collectAsState().value

    HomeScreen(state = state, modifier = modifier, onProductClicked = onProductClicked)

}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onProductClicked: (Int) -> Unit
) {

    Log.i("teste", state.toString())
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        HighlightView(state = state.highlightUiState, onProductClicked = onProductClicked)
        CategoriesView(modifier = Modifier, state.categoryUiState, onProductClicked)
    }
}

@Composable
fun HighlightView(
    modifier: Modifier = Modifier,
    state: HighlightUiState,
    onProductClicked: (Int) -> Unit
) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {

        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(text = state.error, color = MaterialTheme.colorScheme.primary)
            }

            state.product != null -> {

                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    model = state.product.pictureUrl,
                    placeholder = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                OutlinedButton(
                    onClick = { onProductClicked(state.product.id) },
                    modifier = Modifier.padding(bottom = 12.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange600
                    ),

                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.show_more),
                        color = Color.White
                    )
                }
            }

        }

    }
}

@Composable
private fun CategoriesView(
    modifier: Modifier = Modifier,
    state: CategoryUiState,
    onProductClicked: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(text = state.error, color = MaterialTheme.colorScheme.primary)
            }

            else -> {
                HomeScreen(
                    modifier,
                    categories = state.categories,
                    onProductClicked = onProductClicked
                )
            }
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    categories: List<CategoryResponse>,
    onProductClicked: (Int) -> Unit
) {

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(
                vertical = 20.dp
            )
        ) {

            items(categories, key = { it.name }) { cat ->

                Text(
                    text = cat.name,
                    modifier = Modifier.padding(start = 12.dp, bottom = 12.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    LazyRow(
                        contentPadding = PaddingValues(
                            horizontal = 20.dp
                        )
                    ) {

                        items(cat.products, key = { it.name }) { prod ->

                            Column(
                                modifier = Modifier
                                    .widthIn(max = 160.dp)
                                    .padding(start = 8.dp, end = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(140.dp, 188.dp)
                                        .border(
                                            BorderStroke(0.3.dp, color = Color.Gray),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            onProductClicked(prod.id)
                                        },
                                    placeholder = painterResource(id = R.drawable.logo),
                                    model = prod.pictureUrl,
                                    contentDescription = prod.name
                                )
                                Text(

                                    modifier = Modifier.fillMaxWidth(),
                                    text = prod.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(4.dp)
                                        ),
                                    text = prod.price.currency(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.surface,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenLightPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = false
    ) {

        val state = HomeUiState(
            categoryUiState = CategoryUiState(isLoading = true)
        )
        HomeScreen(state = state, onProductClicked = {})

    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenDarkPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = true
    ) {

        val state = HomeUiState(
            categoryUiState = CategoryUiState(error = "Erro de teste")
        )
        HomeScreen(state = state, onProductClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenEmptyDarkPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = true
    ) {

        val state = HomeUiState(
            categoryUiState = CategoryUiState(categories = emptyList())
        )
        HomeScreen(state = state, onProductClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun HighlightPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = false
    ) {

        val state = HomeUiState(
            highlightUiState = HighlightUiState(
                product = HighlightProductResponse(
                    id = 0,
                    productId = 0,
                    pictureUrl = "https://placehold.co/600x400",
                    createdDate = Date()
                )
            )
        )
        HomeScreen(state = state, onProductClicked = {})
    }
}