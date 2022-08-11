package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import javax.inject.Inject

class CheckIsAddedUseCase @Inject constructor(
    private val repository: ProductsRepository
) {

    suspend operator fun invoke(id: Int) : Boolean {
        return repository.isAdded(id)
    }
}