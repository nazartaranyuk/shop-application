package com.nazartaraniuk.shopapplication.data.repository

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import io.reactivex.rxjava3.core.Observable

interface ProductsRemoteDataSource {
    suspend fun getAllCategories() : Result<List<String>>
    suspend fun getAllProducts() : Result<List<ProductItem>>
    fun searchAllProducts() : Observable<List<ProductItem>>
}
