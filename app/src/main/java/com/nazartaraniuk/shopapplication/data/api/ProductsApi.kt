package com.nazartaraniuk.shopapplication.data.api

import retrofit2.http.GET

interface ProductsApi {

    @GET("categories")
    suspend fun getAllCategories() : List<String>

}