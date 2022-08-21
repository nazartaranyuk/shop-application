package com.nazartaraniuk.shopapplication.presentation.models

import androidx.annotation.NavigationRes
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class TitleItemModel(
    val title: String,
    @NavigationRes val link: Int
) : DisplayableItem {

    override fun id(): String = title
}