package com.nazartaraniuk.shopapplication.presentation.di

import android.app.Application
import com.nazartaraniuk.shopapplication.MainActivity
import com.nazartaraniuk.shopapplication.presentation.explore_screen.ExploreFragment
import com.nazartaraniuk.shopapplication.presentation.favorites_screen.FavoritesFragment
import com.nazartaraniuk.shopapplication.presentation.home_screen.HomeFragment
import com.nazartaraniuk.shopapplication.presentation.pdp_screen.ProductPageFragment
import com.nazartaraniuk.shopapplication.presentation.search_screen.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules=[
    NetworkModule::class,
    AppModule::class,
    ViewModelModule::class,
    BindModule::class,
    AppSubcomponentsModule::class,
    UtilsModule::class,
    RoomModule::class
])
@Singleton
interface AppComponent {

    fun homeSubcomponent() : HomeSubcomponent.Builder
    fun exploreSubcomponent(): ExploreSubcomponent.Builder
    fun favoritesSubcomponent(): FavoriteSubcomponent.Builder
    fun productPageSubcomponent(): ProductPageSubcomponent.Builder
    fun searchSubcomponent() : SearchSubcomponent.Builder
    fun accountSubcomponent() : AccountSubcomponent.Builder
    fun inject(activity: MainActivity)

    @Component.Builder
    interface ComponentBuilder {

        fun build() : AppComponent

        @BindsInstance
        fun application(application: Application) : ComponentBuilder
    }

}