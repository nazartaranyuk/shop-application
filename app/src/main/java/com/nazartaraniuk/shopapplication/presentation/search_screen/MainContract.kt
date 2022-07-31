package com.nazartaraniuk.shopapplication.presentation.search_screen

import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel

interface MainContract {

    interface Presenter {
        fun getAllData()
    }

    interface View {
        fun displayData(list: List<ProductItemModel>)
        fun displayError(message: String)
    }
}