package com.nazartaraniuk.shopapplication.presentation.common

import com.nazartaraniuk.shopapplication.databinding.CategorySmallItemBinding
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.models.*
import javax.inject.Singleton

// TODO: Provide here resource provider and retrieve "All" string from resources
@Singleton
object ExploreFragmentUIComposer {

    fun composeInterface(
        firstList: List<CategoryItemModel>,
        secondList: List<ProductItemModel>,
        currentSelectedCategory: String,
    ): List<DisplayableItem> {

        val selectedCategory = currentSelectedCategory.ifEmpty { "All" }

        val list =
            (mutableListOf(CategoryItemModel(category = "All")) + firstLetterToUpperCase(firstList)).map { category ->
                if (category.category == selectedCategory) {
                    category.copy(isSelected = true)
                } else {
                    category
                }
            }

        return listOf(
            CategoriesSmallListModel(
                categories = list
            ),
            ProductListModel(
                productItems = secondList.filter {
                    selectedCategory == "All" || selectedCategory.contains(it.category, true)
                }
            )
        )
    }

    fun composeCategoriesList(
        categoriesList: List<CategoryItemModel>,
        currentSelectedCategory: String,
    ): CategoriesSmallListModel {
        val selectedCategory = currentSelectedCategory.ifEmpty { "All" }

        val list =
            (mutableListOf(CategoryItemModel(category = "All")) + firstLetterToUpperCase(categoriesList)).map { category ->
                if (category.category == selectedCategory) {
                    category.copy(isSelected = true)
                } else {
                    category
                }
            }
        return CategoriesSmallListModel(list)
    }

    fun composeProductList(
        productList: List<ProductItemModel>,
        currentSelectedCategory: String,
    ): ProductListModel {
        val selectedCategory = currentSelectedCategory.ifEmpty { "All" }

        return ProductListModel(
            productItems = productList.filter {
                selectedCategory == "All" || selectedCategory.contains(it.category, true)
            }
        )
    }
}