package com.nazartaraniuk.shopapplication.data.repository

import android.net.Network
import com.nazartaraniuk.shopapplication.domain.common.NetworkResult
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductsRemoteDataSource
) : ProductsRepository {

    override suspend fun fetchCategories(): Flow<NetworkResult<List<String>>> {
        return remoteDataSource.getAllCategories()
    }

}