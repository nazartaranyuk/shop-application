package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: ProductsRepository
) {

    suspend operator fun invoke(): Result<List<String>> {
        return repository.fetchCategories()
    }
}
