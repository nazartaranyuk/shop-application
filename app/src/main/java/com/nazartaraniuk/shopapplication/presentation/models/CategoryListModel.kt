package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class CategoryListModel(
    val categories: List<CategoryItemModel>
) : DisplayableItem {

    override fun id(): String = categories.toString()
}