package com.nazartaraniuk.shopapplication.presentation.models

import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem

data class TitleItemModel(
    val title: String = "Trending now",
    val link: String
) : DisplayableItem