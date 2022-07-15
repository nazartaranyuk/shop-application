package com.nazartaraniuk.shopapplication.data.repository

import com.nazartaraniuk.shopapplication.domain.common.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ProductsRemoteDataSource {
    suspend fun getAllCategories() : Flow<NetworkResult<List<String>>>
}