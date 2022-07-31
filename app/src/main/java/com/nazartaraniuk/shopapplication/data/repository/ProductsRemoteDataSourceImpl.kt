package com.nazartaraniuk.shopapplication.data.repository

import com.nazartaraniuk.shopapplication.data.api.ProductsApi
import com.nazartaraniuk.shopapplication.data.mappers.ApiResponseMapper
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.presentation.exceptions.ApiErrorException
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductsRemoteDataSourceImpl @Inject constructor(
    private val productsApi: ProductsApi,
    private val mapper: ApiResponseMapper
) : ProductsRemoteDataSource {

    override suspend fun getAllCategories(): Result<List<String>> {
       return handleCatching { productsApi.getAllCategories() }
    }

    override suspend fun getAllProducts(): Result<List<ProductItem>> {
      return handleCatching {
           productsApi
               .getAllProducts()
               .map(mapper::toProductItem)
       }
    }
    override fun searchAllProducts(): Observable<List<ProductItem>> {
        return productsApi.searchAllProducts()
            .subscribeOn(Schedulers.io())
            .map { list ->
                list.map { item ->
                    mapper.toProductItem(item)
                }
            }
    }

    private inline fun <R> handleCatching(block: () -> R): Result<R> {
        return try {
            Result.success(block())
        } catch (e: HttpException) {
            Result.failure(ApiErrorException("Error ${e.code()}"))
        } catch (e: IOException) {
            Result.failure(ApiErrorException("You don't have internet connection"))
        }
    }
}