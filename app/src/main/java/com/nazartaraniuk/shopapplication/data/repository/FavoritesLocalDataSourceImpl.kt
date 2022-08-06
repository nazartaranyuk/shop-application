package com.nazartaraniuk.shopapplication.data.repository

import com.nazartaraniuk.shopapplication.data.db.FavoritesDao
import com.nazartaraniuk.shopapplication.data.mappers.ApiResponseMapper
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoritesLocalDataSourceImpl @Inject constructor(
    private val favoritesDao: FavoritesDao,
    private val dispatcher: CoroutineDispatcher,
    private val mapper: ApiResponseMapper
) : FavoritesLocalDataSource {

    override suspend fun loadAllFavorites(): Flow<List<ProductItem>> {
        val favoritesFlow = favoritesDao.getFavorites()
        return favoritesFlow.map { list ->
            list.map { item ->
                mapper.toProductItem(item)
            }
        }
    }

    override suspend fun insertToDatabase(item: ProductItem) = withContext(dispatcher) {
        favoritesDao.addToFavorites(mapper.toProductItemEntity(item))
    }
}