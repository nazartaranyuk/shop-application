package com.nazartaraniuk.shopapplication.data.db

import androidx.room.*
import com.nazartaraniuk.shopapplication.data.entities.ProductItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(item: ProductItemEntity)

    @Query("SELECT * FROM favorites")
    fun getFavorites() : Flow<List<ProductItemEntity>>

    @Delete
    suspend fun deleteFavorite(item: ProductItemEntity)

    @Query("SELECT * FROM favorites WHERE id=:id")
    suspend fun getItemById(id: Int) : ProductItemEntity?
}