package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import com.nazartaraniuk.shopapplication.presentation.exceptions.ProductItemException
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSingleProductUseCase @Inject constructor(
    private val repository: ProductsRepository,
    private val mapper: ToUiModelMapper
) {
    suspend operator fun invoke(id: Int): Flow<Result<ProductItemModel>> = flow {
        val singleProduct = repository.fetchSingleProduct(id)

        if (singleProduct.isFailure) {
            val throwable = singleProduct.exceptionOrNull()
            emit(Result.failure(throwable ?: ProductItemException("Error")))
        } else {
            emit(Result.success(
                mapper.toProductItemModel(
                    singleProduct.getOrNull() ?: throw ProductItemException(MESSAGE)
                )
            ))
        }
    }
    companion object {
        const val MESSAGE = "Can't find item, maybe wrong item id"
    }
}