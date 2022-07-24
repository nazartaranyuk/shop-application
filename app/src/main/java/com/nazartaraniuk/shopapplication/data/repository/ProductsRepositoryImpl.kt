package com.nazartaraniuk.shopapplication.data.repository

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductsRemoteDataSource
) : ProductsRepository {

    override suspend fun fetchCategories(): Result<List<String>> {
        return remoteDataSource.getAllCategories()
    }

    override suspend fun fetchProducts(): Result<List<ProductItem>> {
        return remoteDataSource.getAllProducts()
    }

}
