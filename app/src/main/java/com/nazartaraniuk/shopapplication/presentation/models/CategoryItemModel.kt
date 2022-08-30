package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class CategoryItemModel(
    val category: String,
    val isSelected: Boolean = false,
) : DisplayableItem {

    override fun id(): String = category

    override fun payload(other: Any): DisplayableItem.Payloadable {
        return DisplayableItem.Payloadable.None
    }
}
