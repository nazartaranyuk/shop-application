package com.nazartaraniuk.shopapplication.presentation.di

import com.nazartaraniuk.shopapplication.presentation.explore_screen.ExploreFragment
import dagger.Subcomponent

@Subcomponent(modules = [
    UtilsModule::class,
    NetworkModule::class,
    BindModule::class,
    ViewModelModule::class,
    AppModule::class
])
interface ExploreSubcomponent {

    fun inject(fragment: ExploreFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): ExploreSubcomponent
    }
}