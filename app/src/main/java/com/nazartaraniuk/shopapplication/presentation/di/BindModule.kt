package com.nazartaraniuk.shopapplication.presentation.di

import com.nazartaraniuk.shopapplication.data.repository.ProductsRemoteDataSource
import com.nazartaraniuk.shopapplication.data.repository.ProductsRemoteDataSourceImpl
import com.nazartaraniuk.shopapplication.data.repository.ProductsRepositoryImpl
import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import com.nazartaraniuk.shopapplication.presentation.search_screen.MainContract
import com.nazartaraniuk.shopapplication.presentation.search_screen.SearchFragmentPresenter
import dagger.Binds
import dagger.Module

@Module
interface BindModule {

    @Binds
    fun bindRepository(repository: ProductsRepositoryImpl) : ProductsRepository

    @Binds
    fun bindDataSource(dataSource: ProductsRemoteDataSourceImpl) : ProductsRemoteDataSource

    @Binds
    fun bindPresenter(presenter: SearchFragmentPresenter) : MainContract.Presenter

}
