package com.nazartaraniuk.shopapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nazartaraniuk.shopapplication.data.entities.ProductItemEntity

@Database(entities = [ProductItemEntity::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun getFavoritesDao() : FavoritesDao
}