package com.nazartaraniuk.shopapplication.presentation.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nazartaraniuk.shopapplication.data.db.FavoritesDao
import com.nazartaraniuk.shopapplication.data.db.FavoritesDatabase
import com.nazartaraniuk.shopapplication.data.mappers.ApiResponseMapper
import com.nazartaraniuk.shopapplication.data.repository.FavoritesLocalDataSource
import com.nazartaraniuk.shopapplication.data.repository.FavoritesLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    fun provideDao(roomDatabase: FavoritesDatabase) : FavoritesDao = roomDatabase.getFavoritesDao()

    @Provides
    @Singleton
    fun provideDatabase(context: Context): FavoritesDatabase = Room.databaseBuilder(
        context,
        FavoritesDatabase::class.java,
        "favorites_database"
        ).build()
}