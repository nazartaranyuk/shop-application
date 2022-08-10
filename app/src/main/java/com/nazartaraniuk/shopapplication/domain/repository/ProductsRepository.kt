package com.nazartaraniuk.shopapplication.domain.repository

import com.nazartaraniuk.shopapplication.data.entities.ProductItemEntity
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun fetchCategories(): Result<List<String>>
    suspend fun fetchSingleProduct(id: Int): Result<ProductItem>

    // Functions below are the same, but I need them for two different architecture principles
    suspend fun fetchProducts(): Result<List<ProductItem>>
    fun searchProducts(): Observable<List<ProductItem>>

    suspend fun fetchFavorites(): Flow<List<ProductItem>>
    suspend fun addToFavorites(item: ProductItemEntity)
    suspend fun deleteFromFavorites(item: ProductItem)
}
