package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class CategoriesSmallListModel(
    val categories: List<CategoryItemModel>
) : DisplayableItem {

    override fun id(): String = categories.toString()
}