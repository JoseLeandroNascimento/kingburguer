package com.example.kingburguer.composes.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.example.kingburguer.R
import com.example.kingburguer.common.currency
import com.example.kingburguer.ui.theme.KingburguerTheme
import com.example.kingburguer.ui.theme.Orange600

data class Product(
    val name: String,
    @DrawableRes val picture: Int = R.drawable.example,
    val price: Double = 19.9
)

data class Category(
    val name: String,
    val products: List<Product>
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val categories = listOf(
        Category(
            name = "Vegetariano",
            products = listOf(
                Product(name = "Combo v1"),
                Product(name = "Combo v2"),
                Product(name = "Combo v3"),
            )
        ),
        Category(
            name = "Bovina",
            products = listOf(
                Product(name = "Combo b1 asdfasdasd as d as d as d as da s"),
                Product(name = "Combo b2"),
                Product(name = "Combo b3"),
                Product(name = "Combo b4"),
                Product(name = "Combo b5"),
                Product(name = "Combo b6"),
            )
        ),
        Category(
            name = "Sobremesa",
            products = listOf(
                Product(name = "Sobremesa s1"),
                Product(name = "Sobremesa s2"),
                Product(name = "Sobremesa s3"),
                Product(name = "Sobremesa s4"),
                Product(name = "Sobremesa s5"),
                Product(name = "Sobremesa s6"),
            )
        )
    )

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        Column(

        ) {

            Box(
                contentAlignment = Alignment.BottomCenter
            ) {

                Image(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .background(Color.Blue),
                    painter = painterResource(R.drawable.highlight),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                OutlinedButton(
                    onClick = {},
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
                        text = stringResource(id = R.string.get_coupon),
                        color = Color.White
                    )
                }
            }
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
                                    Image(
                                        modifier = Modifier
                                            .size(140.dp, 188.dp)
                                            .border(
                                                BorderStroke(0.3.dp, color = Color.Gray),
                                                RoundedCornerShape(8.dp)
                                            ),
                                        painter = painterResource(id = prod.picture),
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
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenLightPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        HomeScreen()

    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenDarkPreview() {

    KingburguerTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        HomeScreen()
    }
}