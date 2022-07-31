package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class ImageItemModel(
    val image: String
) : DisplayableItem {

    override fun id(): String = image
}