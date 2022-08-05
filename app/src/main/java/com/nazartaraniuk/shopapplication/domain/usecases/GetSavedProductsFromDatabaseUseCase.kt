package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedProductsFromDatabaseUseCase @Inject constructor(
    private val repository: ProductsRepository,
) {

    suspend operator fun invoke(): Flow<List<ProductItem>> {
        return repository.fetchFavorites()
    }
}