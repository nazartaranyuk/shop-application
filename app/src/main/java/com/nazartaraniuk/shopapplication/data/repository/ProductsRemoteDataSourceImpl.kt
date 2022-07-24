package com.nazartaraniuk.shopapplication.data.repository

import com.nazartaraniuk.shopapplication.data.api.ProductsApi
import com.nazartaraniuk.shopapplication.data.mappers.ApiResponseMapper
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.presentation.exceptions.ApiErrorException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductsRemoteDataSourceImpl @Inject constructor(
    private val productsApi: ProductsApi,
    private val mapper: ApiResponseMapper
) : ProductsRemoteDataSource {

    override suspend fun getAllCategories(): Result<List<String>> {
        return try {
            Result.success(productsApi.getAllCategories())
        } catch (e: HttpException) {
            Result.failure(ApiErrorException("Error ${e.code()}"))
        } catch (e: IOException) {
            Result.failure(ApiErrorException("You don't have internet connection"))
        }
    }

    override suspend fun getAllProducts(): Result<List<ProductItem>> {
        return try {
            val list = productsApi.getAllProducts().map { mapper.toProductItem(it) }
            Result.success(list)
        } catch (e: HttpException) {
            Result.failure(ApiErrorException("Error ${e.code()}"))
        } catch (e: IOException) {
            Result.failure(ApiErrorException("You don't have internet connection"))
        }
    }
}
