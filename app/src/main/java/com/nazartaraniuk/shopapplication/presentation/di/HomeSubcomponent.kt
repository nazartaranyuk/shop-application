package com.nazartaraniuk.shopapplication.presentation.di

import com.nazartaraniuk.shopapplication.presentation.home_screen.HomeFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        AppModule::class,
        UtilsModule::class,
        ViewModelModule::class]
)
interface HomeSubcomponent {

    fun inject(fragment: HomeFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): HomeSubcomponent
    }
}