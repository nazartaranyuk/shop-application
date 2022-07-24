package com.nazartaraniuk.shopapplication.data.repository

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem

interface ProductsRemoteDataSource {
    suspend fun getAllCategories() : Result<List<String>>
    suspend fun getAllProducts() : Result<List<ProductItem>>
}
