package com.nazartaraniuk.shopapplication.presentation.di

import android.app.Application
import android.content.Context
import com.nazartaraniuk.shopapplication.presentation.common.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class AppModule {

    @Provides
    fun provideContext(app: Application): Context = app

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
