package com.nazartaraniuk.shopapplication.data.repository

import com.nazartaraniuk.shopapplication.data.api.ProductsApi
import com.nazartaraniuk.shopapplication.data.mappers.ApiResponseMapper
import com.nazartaraniuk.shopapplication.domain.common.NetworkResult
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductsRemoteDataSourceImpl @Inject constructor(
    private val productsApi: ProductsApi,
) : ProductsRemoteDataSource {

    override suspend fun getAllCategories(): Flow<NetworkResult<List<String>>> = flow {

        emit(NetworkResult.Loading())

        try {
            val response = productsApi.getAllCategories()
            emit(NetworkResult.Success(response))
        } catch (e: HttpException) {
            emit(
                NetworkResult.Error(e.message ?: "Error ${e.code()}")
            )
        } catch (e: IOException) {
            emit(NetworkResult.Error(e.message ?: "Unknown error"))
        }

    }

    override suspend fun getAllProducts(): Flow<NetworkResult<List<ProductItem>>> = flow {

        emit(NetworkResult.Loading())

        try {
            val response = productsApi.getAllProducts()
            emit(NetworkResult.Success(response.map {
                ApiResponseMapper.toProductItem(it)
            }))
        } catch (e: HttpException) {
            emit(
                NetworkResult.Error(e.message ?: "Error ${e.code()}")
            )
        } catch (e: IOException) {
            emit(
                NetworkResult.Error(e.message ?: "Unknown error")
            )
        }
    }
}