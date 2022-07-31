package com.nazartaraniuk.shopapplication.presentation.common

sealed class Events(val message: String) {
    class Error(message: String) : Events(message)
}