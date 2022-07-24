package com.nazartaraniuk.shopapplication.domain.repository

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem

interface ProductsRepository {
    suspend fun fetchCategories() : Result<List<String>>
    suspend fun fetchProducts() : Result<List<ProductItem>>
}
