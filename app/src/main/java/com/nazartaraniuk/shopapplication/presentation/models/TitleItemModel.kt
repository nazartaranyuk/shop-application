package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class TitleItemModel(
    val title: String,
) : DisplayableItem {

    override fun id(): String = title
}