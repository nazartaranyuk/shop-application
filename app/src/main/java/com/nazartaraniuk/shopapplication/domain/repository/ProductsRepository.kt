package com.nazartaraniuk.shopapplication.domain.repository

import android.net.Network
import com.nazartaraniuk.shopapplication.domain.common.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun fetchCategories() : Flow<NetworkResult<List<String>>>
}