package com.nazartaraniuk.shopapplication.data.repository

import com.nazartaraniuk.shopapplication.data.entities.ProductItemEntity
import com.nazartaraniuk.shopapplication.data.mappers.ApiResponseMapper
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductsRemoteDataSource,
    private val localDataSource: FavoritesLocalDataSource,
    private val mapper: ApiResponseMapper
) : ProductsRepository {

    override suspend fun fetchCategories(): Result<List<String>> {
        return remoteDataSource.getAllCategories()
    }

    override suspend fun fetchSingleProduct(id: Int): Result<ProductItem> {
        return remoteDataSource.getSingleProduct(id)
    }

    override suspend fun fetchProducts(): Result<List<ProductItem>> {
        return remoteDataSource.getAllProducts()
    }

    override fun searchProducts(): Observable<List<ProductItem>> {
        return remoteDataSource.searchAllProducts()
    }

    override suspend fun fetchFavorites(): Flow<List<ProductItem>> {
        return localDataSource.loadAllFavorites()
    }

    override suspend fun addToFavorites(item: ProductItemEntity) {
        return localDataSource.insertToDatabase(mapper.toProductItem(item))
    }

    override suspend fun deleteFromFavorites(item: ProductItem) {
        return localDataSource.deleteFromDatabase(item)
    }

}
