package com.nazartaraniuk.shopapplication.domain.repository

import android.net.Network
import com.nazartaraniuk.shopapplication.domain.common.NetworkResult
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun fetchCategories() : Flow<NetworkResult<List<String>>>
    suspend fun fetchProducts() : Flow<NetworkResult<List<ProductItem>>>
}