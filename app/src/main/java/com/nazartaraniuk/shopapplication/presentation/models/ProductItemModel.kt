package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.domain.entities.Rating
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class ProductItemModel(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
) : DisplayableItem {

    override fun id(): String = title
}