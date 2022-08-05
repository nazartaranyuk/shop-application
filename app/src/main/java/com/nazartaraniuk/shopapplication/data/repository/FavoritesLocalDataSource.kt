package com.nazartaraniuk.shopapplication.data.repository

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import kotlinx.coroutines.flow.Flow

interface FavoritesLocalDataSource {

    suspend fun loadAllFavorites(): Flow<List<ProductItem>>
    suspend fun insertToDatabase(item: ProductItem)
}