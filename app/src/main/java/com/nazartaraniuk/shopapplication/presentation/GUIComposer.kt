package com.nazartaraniuk.shopapplication.presentation

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.models.*

object GUIComposer {

    fun composeInterface(
        firstList: List<CategoryItemModel>,
        secondList: List<ProductItemModel>
    ): List<DisplayableItem> {
        return mutableListOf(
            ImageItemModel(
                "Some image"
            ),
            TitleItemModel(
                "Trending now",
                "some link"
            ),
            TrendingListModel(
                trendingItems = secondList
            ),
            TitleItemModel(
                "Browse categories",
                "some link"
            ),
            CategoryListModel(
                categories = firstList
            )
        )
    }
}