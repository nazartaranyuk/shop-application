package com.nazartaraniuk.shopapplication.presentation.common

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.models.*
import javax.inject.Singleton

@Singleton
object ExploreFragmentUIComposer {

    fun composeInterface(
        firstList: List<CategoryItemModel>,
        secondList: List<ProductItemModel>
    ): List<DisplayableItem> {
        return listOf(
            CategoriesSmallListModel(
                categories = mutableListOf(CategoryItemModel("All")).also {
                    it.addAll(firstLetterToUpperCase(firstList))
                }
            ),
            ProductListModel(
                productItems = secondList
            )
        )
    }
}