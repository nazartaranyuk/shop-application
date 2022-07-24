package com.nazartaraniuk.shopapplication.presentation

sealed class Events<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : Events<T>()
    class Success<T>(data: T) : Events<T>(data)
    class Error<T>(message: String, data: T? = null) : Events<T>(data, message)
}