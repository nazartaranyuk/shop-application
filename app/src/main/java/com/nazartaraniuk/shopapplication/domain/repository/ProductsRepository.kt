package com.nazartaraniuk.shopapplication.domain.repository

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import io.reactivex.rxjava3.core.Observable

interface ProductsRepository {
    suspend fun fetchCategories() : Result<List<String>>
    // Functions below are the same, but I need them for two different architecture principles
    suspend fun fetchProducts() : Result<List<ProductItem>>
    fun searchProducts() : Observable<List<ProductItem>>
}
