package com.nazartaraniuk.shopapplication.presentation.di

import com.nazartaraniuk.shopapplication.presentation.favorites_screen.FavoritesFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        ViewModelModule::class,
        BindModule::class,
        RoomModule::class,
        AppModule::class
    ]
)
interface FavoriteSubcomponent {

    fun inject(fragment: FavoritesFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): FavoriteSubcomponent
    }
}