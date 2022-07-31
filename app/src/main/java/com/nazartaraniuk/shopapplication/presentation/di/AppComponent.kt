package com.nazartaraniuk.shopapplication.presentation.di

import android.app.Application
import com.nazartaraniuk.shopapplication.presentation.explore_screen.ExploreFragment
import com.nazartaraniuk.shopapplication.presentation.home_screen.HomeFragment
import com.nazartaraniuk.shopapplication.presentation.search_screen.SearchFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules=[NetworkModule::class, AppModule::class, ViewModelModule::class, BindModule::class, UtilsModule::class])
interface AppComponent {

    fun inject(fragment: HomeFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: ExploreFragment)

    @Component.Builder
    interface ComponentBuilder {

        fun build() : AppComponent

        @BindsInstance
        fun application(application: Application) : ComponentBuilder
    }

}