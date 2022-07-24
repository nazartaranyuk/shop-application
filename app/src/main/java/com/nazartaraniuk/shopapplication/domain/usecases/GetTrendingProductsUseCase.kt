package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import javax.inject.Inject

class GetTrendingProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke() : Result<List<ProductItem>> {
        return repository.fetchProducts()
    }
}
