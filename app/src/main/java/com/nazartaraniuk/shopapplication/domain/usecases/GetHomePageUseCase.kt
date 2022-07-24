package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import com.nazartaraniuk.shopapplication.presentation.common.GUIComposer
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetHomePageUseCase @Inject constructor(
    private val repository: ProductsRepository,
    private val mapper: ToUiModelMapper,
    private val composer: GUIComposer
) {

    suspend operator fun invoke(): Flow<Result<List<DisplayableItem>>> =
        withContext(Dispatchers.IO) {
            flow {
                val categories = repository.fetchCategories()
                val trendingProducts = repository.fetchProducts()

                if (categories.isFailure || trendingProducts.isFailure) {
                    val throwable = categories.exceptionOrNull()
                    emit(Result.failure(throwable ?: Exception("Error")))
                } else {
                    val listOfCategories = categories.getOrNull() ?: emptyList()
                    val listOfTrendingProducts = trendingProducts.getOrNull() ?: emptyList()

                    emit(
                        Result.success(
                            composer.composeInterface(
                                firstList = listOfCategories.map(mapper::toCategoryItemModel),
                                secondList = createTrendingList(listOfTrendingProducts)
                            )
                        )
                    )
                }
            }
        }

    private fun createTrendingList(list: List<ProductItem>?): List<ProductItemModel> {
        if (list.isNullOrEmpty()) {
            return emptyList()
        }

        val newList = mutableListOf<ProductItemModel>()
        // Here I sort the entry list by rating and put the first five values into a new list
        list.sortedBy { it.rating.rate }
        var i = 0
        while (i < 5) {
            val tempItem =
                ToUiModelMapper.toProductItemModel(list[i])
            newList.add(tempItem)
            i++
        }
        return newList
    }

}
