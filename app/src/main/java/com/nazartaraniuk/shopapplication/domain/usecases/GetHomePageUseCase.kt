package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.common.HomeFragmentUIComposer
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetHomePageUseCase @Inject constructor(
    private val repository: ProductsRepository,
    private val mapper: ToUiModelMapper,
    private val composer: HomeFragmentUIComposer
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

    private fun createTrendingList(list: List<ProductItem>): List<ProductItemModel> {
        return list
            .sortedBy { it.rating.rate }
            .take(5)
            .map { item -> mapper.toProductItemModel(item) }
    }

}
