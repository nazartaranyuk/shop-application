package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetExplorePageUseCase @Inject constructor(
    private val repository: ProductsRepository,
    private val mapper: ToUiModelMapper,
) {
    suspend operator fun invoke(): Flow<Result<Pair<List<CategoryItemModel>, List<ProductItemModel>>>> =
        withContext(Dispatchers.IO) {
            flow {
                val categories = repository.fetchCategories()
                val products = repository.fetchProducts()

                if (categories.isFailure || products.isFailure) {
                    val throwable = categories.exceptionOrNull()
                    emit(Result.failure(throwable ?: Exception("Error")))
                } else {
                    val listOfCategories = categories.getOrNull() ?: emptyList()
                    val listOfProducts = products.getOrNull() ?: emptyList()

                    emit(
                        Result.success(
                            Pair(
                                listOfCategories.map(mapper::toCategoryItemModel),
                                listOfProducts.map(mapper::toProductItemModel)
                            )
                        )
                    )
                }
            }
        }
}
