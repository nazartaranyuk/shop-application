package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.common.NetworkResult
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke() : Flow<NetworkResult<List<ProductItem>>> {
        return repository.fetchProducts()
    }
}