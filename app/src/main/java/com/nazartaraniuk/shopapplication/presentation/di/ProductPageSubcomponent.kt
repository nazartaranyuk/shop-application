package com.nazartaraniuk.shopapplication.presentation.di

import com.nazartaraniuk.shopapplication.presentation.pdp_screen.ProductPageFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        ViewModelModule::class,
        BindModule::class,
        RoomModule::class,
        AppModule::class
    ]
)
interface ProductPageSubcomponent {

    fun inject(fragment: ProductPageFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): ProductPageSubcomponent
    }
}