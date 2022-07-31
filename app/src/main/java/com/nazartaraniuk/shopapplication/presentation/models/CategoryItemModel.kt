package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class CategoryItemModel(
    val category: String
) : DisplayableItem {

    override fun id(): String = category
}
