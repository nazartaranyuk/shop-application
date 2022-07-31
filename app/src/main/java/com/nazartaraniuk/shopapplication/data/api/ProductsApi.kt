package com.nazartaraniuk.shopapplication.data.api

import com.nazartaraniuk.shopapplication.data.entities.ProductItemEntity
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ProductsApi {

    @GET("products/categories")
    suspend fun getAllCategories() : List<String>

    @GET("products")
    suspend fun getAllProducts() : List<ProductItemEntity>

    @GET("products")
    fun searchAllProducts() : Observable<List<ProductItemEntity>>
}