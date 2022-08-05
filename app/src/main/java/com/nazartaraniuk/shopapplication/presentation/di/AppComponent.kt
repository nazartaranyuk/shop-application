package com.nazartaraniuk.shopapplication.presentation.di

import android.app.Application
import android.content.Context
import com.nazartaraniuk.shopapplication.presentation.explore_screen.ExploreFragment
import com.nazartaraniuk.shopapplication.presentation.home_screen.HomeFragment
import com.nazartaraniuk.shopapplication.presentation.pdp_screen.ProductPageFragment
import com.nazartaraniuk.shopapplication.presentation.search_screen.SearchFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import javax.inject.Singleton

@Component(modules=[
    NetworkModule::class,
    AppModule::class,
    ViewModelModule::class,
    BindModule::class,
    UtilsModule::class,
    RoomModule::class
])
@Singleton
interface AppComponent {

    fun inject(fragment: HomeFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: ExploreFragment)
    fun inject(fragment: ProductPageFragment)

    @Component.Builder
    interface ComponentBuilder {

        fun build() : AppComponent

        @BindsInstance
        fun application(application: Application) : ComponentBuilder
    }

}