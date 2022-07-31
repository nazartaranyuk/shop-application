package com.nazartaraniuk.shopapplication.presentation.di

import com.nazartaraniuk.shopapplication.domain.usecases.GetSearchProductsUseCase
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import com.nazartaraniuk.shopapplication.presentation.search_screen.MainContract
import com.nazartaraniuk.shopapplication.presentation.search_screen.SearchFragmentPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun providePresenter(
        getSearchProductsUseCase: GetSearchProductsUseCase,
        mapper: ToUiModelMapper
        ): MainContract.Presenter = SearchFragmentPresenter(getSearchProductsUseCase, mapper)

}