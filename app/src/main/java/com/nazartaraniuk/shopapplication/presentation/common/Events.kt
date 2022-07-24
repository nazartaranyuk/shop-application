package com.nazartaraniuk.shopapplication.presentation.common

sealed class Events<T>(val message: String? = null) {
    class Error<T>(message: String) : Events<T>(message)
}