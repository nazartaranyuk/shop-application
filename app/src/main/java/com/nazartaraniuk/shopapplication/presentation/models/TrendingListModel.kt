package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class TrendingListModel(
    val trendingItems: List<ProductItemModel>
) : DisplayableItem