package com.nazartaraniuk.shopapplication.presentation.common

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.models.*
import javax.inject.Singleton

@Singleton
object HomeFragmentUIComposer {

    fun composeInterface(
        firstList: List<CategoryItemModel>,
        secondList: List<ProductItemModel>
    ): List<DisplayableItem> {
        return listOf(
            ImageItemModel(
                "Some image"
            ),
            TitleItemModel(
                "Trending now",
            ),
            ProductListModel(
                productItems = secondList
            ),
            TitleItemModel(
                "Browse categories",
            ),
            CategoryListModel(
                categories = firstLetterToUpperCase(firstList)
            )
        )
    }
}