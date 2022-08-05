package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class ProductListModel(
    val productItems: List<ProductItemModel>
) : DisplayableItem {

    override fun id(): String = productItems.toString()
}