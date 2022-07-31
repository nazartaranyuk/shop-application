package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetSearchProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    operator fun invoke(): Observable<List<ProductItem>> {
        return repository.searchProducts()
    }
}