package com.nazartaraniuk.shopapplication.domain.common

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : NetworkResult<T>()
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
}