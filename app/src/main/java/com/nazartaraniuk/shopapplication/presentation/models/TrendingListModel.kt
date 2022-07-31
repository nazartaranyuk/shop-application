package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class TrendingListModel(
    val trendingItems: List<ProductItemModel>
) : DisplayableItem {

    override fun id(): String = trendingItems.toString()
}