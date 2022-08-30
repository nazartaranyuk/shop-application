package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class CategoriesSmallListModel(
    val categories: List<CategoryItemModel>
) : DisplayableItem {

    override fun id(): String = categories.toString()

    override fun payload(other: Any): DisplayableItem.Payloadable {
        return DisplayableItem.Payloadable.None
    }
}