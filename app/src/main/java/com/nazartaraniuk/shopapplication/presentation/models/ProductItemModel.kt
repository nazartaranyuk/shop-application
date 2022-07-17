package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.domain.entities.Rating

data class ProductItemModel(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)