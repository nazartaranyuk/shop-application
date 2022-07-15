package com.nazartaraniuk.shopapplication.presentation.di

import android.app.Application

class MainApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }
}

fun Application.getComponent(): AppComponent {
    return (this as MainApplication).appComponent
}
