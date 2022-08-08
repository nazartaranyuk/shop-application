package com.nazartaraniuk.shopapplication.presentation.di

import com.nazartaraniuk.shopapplication.presentation.search_screen.SearchFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        PresenterModule::class,
        AppModule::class,
        BindModule::class,
        NetworkModule::class,
        UtilsModule::class
    ]
)
interface SearchSubcomponent {

    fun inject(fragment: SearchFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): SearchSubcomponent
    }
}