package com.nazartaraniuk.shopapplication.data.entities

data class ProductItemEntity(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: RatingEntity,
    val title: String
)