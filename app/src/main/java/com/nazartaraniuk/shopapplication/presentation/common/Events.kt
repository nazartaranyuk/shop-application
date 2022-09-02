package com.nazartaraniuk.shopapplication.presentation.common

import android.view.View
import androidx.annotation.DrawableRes
import com.nazartaraniuk.shopapplication.R


sealed class Events(
    val message: String = "",
    val visibility: Int = View.VISIBLE,
    val resource: Int = R.drawable.ic_favorites_unchecked
) {

    class Error(
        message: String,
        visibility: Int = View.VISIBLE,
    ) : Events(message, visibility)

    class Saved(
        @DrawableRes resource: Int
    ) : Events(resource = resource)

    class UnSaved(
        @DrawableRes resource: Int
    ) : Events(resource = resource)
}