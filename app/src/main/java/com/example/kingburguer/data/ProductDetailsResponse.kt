package com.example.kingburguer.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ProductDetailsResponse(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("picture_url")
    val pictureUrl: String,
    val price: Double,
    @SerializedName("created_date")
    val createdDate: Date,
    val categoryResponse: CategoryDetailsResponse
)

data class CategoryDetailsResponse(
    val id: Int,
    val name: String
)
