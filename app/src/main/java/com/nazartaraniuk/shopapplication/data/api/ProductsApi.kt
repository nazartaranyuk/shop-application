package com.nazartaraniuk.shopapplication.data.api

import com.nazartaraniuk.shopapplication.data.entities.ProductItemEntity
import retrofit2.http.GET

interface ProductsApi {

    @GET("products/categories")
    suspend fun getAllCategories() : List<String>

    @GET("products")
    suspend fun getAllProducts() : List<ProductItemEntity>
}