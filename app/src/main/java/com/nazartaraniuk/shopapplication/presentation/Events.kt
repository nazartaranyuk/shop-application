package com.nazartaraniuk.shopapplication.presentation

sealed class Events {
    object Loading : Events()
    class Success<R>(val data: R) : Events()
    class Error(val message: String) : Events()
}