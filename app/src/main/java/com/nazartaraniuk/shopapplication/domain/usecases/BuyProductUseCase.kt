package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import javax.inject.Inject

class BuyProductUseCase @Inject constructor(
    private val repository: ProductsRepository
){
    suspend operator fun invoke(body: ProductItem) {
        repository.buyProduct(body)
    }
}