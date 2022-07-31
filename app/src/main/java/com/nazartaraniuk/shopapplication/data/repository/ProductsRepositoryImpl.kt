package com.nazartaraniuk.shopapplication.data.repository

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import io.reactivex.rxjava3.core.Observable
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

    override fun searchProducts(): Observable<List<ProductItem>> {
        return remoteDataSource.searchAllProducts()
    }

}
