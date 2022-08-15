package com.nazartaraniuk.shopapplication.presentation.di

import com.nazartaraniuk.shopapplication.presentation.account_screen.AccountFragment
import dagger.Subcomponent

@Subcomponent
interface AccountSubcomponent {

    fun inject(fragment: AccountFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): AccountSubcomponent
    }
}