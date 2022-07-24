package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.common.NetworkResult
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.presentation.GUIComposer
import com.nazartaraniuk.shopapplication.presentation.ToUiModelMapper
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetComposeInterfaceUseCase @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getTrendingProductsUseCase: GetTrendingProductsUseCase
) {

    suspend operator fun invoke(): Flow<NetworkResult<List<DisplayableItem>>> =
        withContext(Dispatchers.IO) {
            flow {

                val categories = async {
                    getCategoriesUseCase()
                }

                val trendingProducts = async {
                    getTrendingProductsUseCase()
                }

                categories.await().combine(trendingProducts.await(), ::Pair)
                    .collect { (categoriesList, trendingList) ->
                        emit(NetworkResult.Loading())
                        if (categoriesList is NetworkResult.Error) {

                            emit(NetworkResult.Error(categoriesList.message ?: "Error"))

                        } else if (trendingList is NetworkResult.Error) {

                            emit(NetworkResult.Error(trendingList.message ?: "Error"))

                        } else {

                            val listOfCategories = categoriesList.data ?: emptyList()
                            val listOfTrendingProducts = trendingList.data ?: emptyList()
                            val displayableList = GUIComposer.composeInterface(
                                firstList = listOfCategories.map(ToUiModelMapper::toCategoryItemModel),
                                secondList = createTrendingList(listOfTrendingProducts)
                            )
                            emit(NetworkResult.Success(displayableList))
                        }
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