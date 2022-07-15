package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.common.NetworkResult
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: ProductsRepository
) {

    suspend operator fun invoke(): Flow<NetworkResult<List<String>>> {
        return repository.fetchCategories()
    }
}