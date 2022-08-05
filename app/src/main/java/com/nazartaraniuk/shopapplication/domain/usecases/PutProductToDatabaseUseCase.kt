package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.mappers.DomainMapper
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import javax.inject.Inject

class PutProductToDatabaseUseCase @Inject constructor(
    private val repository: ProductsRepository,
    private val mapper: DomainMapper
) {

    suspend operator fun invoke(item: ProductItem) {
        repository.addToFavorites(mapper.toProductItemEntity(item))
    }
}