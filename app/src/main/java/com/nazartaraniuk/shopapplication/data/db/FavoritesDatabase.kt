package com.nazartaraniuk.shopapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nazartaraniuk.shopapplication.data.entities.ProductItemEntity

@Database(entities = [ProductItemEntity::class], version = 2, exportSchema = true)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun getFavoritesDao() : FavoritesDao

}