package com.nazartaraniuk.shopapplication.presentation.common

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.models.*
import javax.inject.Singleton

@Singleton
object ExploreFragmentUIComposer {

    fun composeInterface(
        firstList: List<CategoryItemModel>
    ): List<DisplayableItem> {
        return mutableListOf(
            CategoriesSmallListModel(
                categories = firstList
            )
        )
    }
}