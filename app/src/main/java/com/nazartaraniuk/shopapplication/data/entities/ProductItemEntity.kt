package com.nazartaraniuk.shopapplication.data.entities

import com.nazartaraniuk.shopapplication.domain.entities.Rating

data class ProductItemEntity(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)