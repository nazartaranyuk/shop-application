package com.nazartaraniuk.shopapplication.presentation.di

import dagger.Module

@Module(
    subcomponents = [
        HomeSubcomponent::class,
        ExploreSubcomponent::class,
        FavoriteSubcomponent::class
    ]
)
class AppSubcomponentsModule