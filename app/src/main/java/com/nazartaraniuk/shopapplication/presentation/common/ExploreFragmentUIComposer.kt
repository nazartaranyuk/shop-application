package com.nazartaraniuk.shopapplication.presentation.common

import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.presentation.models.CategoriesSmallListModel
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import com.nazartaraniuk.shopapplication.presentation.models.ProductListModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreFragmentUIComposer @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    private val defaultString: String by lazy {
        resourceProvider.getStringFromResources(R.string.all)
    }

    fun composeCategoriesList(
        categoriesList: List<CategoryItemModel>,
        currentSelectedCategory: String,
    ): CategoriesSmallListModel {
        val selectedCategory = currentSelectedCategory.ifEmpty { defaultString }

        val list = (mutableListOf(CategoryItemModel(category = defaultString)) + firstLetterToUpperCase(
            categoriesList
        )).map { category ->
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
        val selectedCategory = currentSelectedCategory.ifEmpty { defaultString }

        return ProductListModel(
            productItems = productList.filter {
                selectedCategory == defaultString || selectedCategory.contains(it.category, true)
            }
        )
    }
}