package com.nazartaraniuk.shopapplication.presentation.di

import android.app.Application
import com.nazartaraniuk.shopapplication.presentation.home.HomeFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules=[NetworkModule::class, AppModule::class, ViewModelModule::class, BindModule::class])
interface AppComponent {

    fun inject(fragment: HomeFragment)

    @Component.Builder
    interface ComponentBuilder {

        fun build() : AppComponent

        @BindsInstance
        fun application(application: Application) : ComponentBuilder
    }

}