package com.nazartaraniuk.shopapplication.presentation.common


sealed class Events(val message: String, val visibility: Int) {
    class Error(
        message: String,
        visibility: Int,
    ) : Events(message, visibility)
}