package com.nazartaraniuk.shopapplication.presentation.pdp_screen

import android.view.View
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.entities.Rating
import com.nazartaraniuk.shopapplication.domain.usecases.DeleteProductFromDatabaseUseCase
import com.nazartaraniuk.shopapplication.domain.usecases.GetSavedProductsFromDatabaseUseCase
import com.nazartaraniuk.shopapplication.domain.usecases.GetSingleProductUseCase
import com.nazartaraniuk.shopapplication.domain.usecases.PutProductToDatabaseUseCase
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.SingleLiveEvent
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductPageViewModel @Inject constructor(
    private val getSingleProductUseCase: GetSingleProductUseCase,
    private val putProductToDatabaseUseCase: PutProductToDatabaseUseCase,
    private val getSavedProductsFromDatabaseUseCase: GetSavedProductsFromDatabaseUseCase,
    private val deleteProductFromDatabaseUseCase: DeleteProductFromDatabaseUseCase,
    private val mapper: ToUiModelMapper,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _errorAction = SingleLiveEvent<Events>()
    val errorAction: LiveData<Events> get() = _errorAction

    private val _loadingState = MutableLiveData<ProductPageViewModelState<ProductItemModel>>()
    val loadingState: LiveData<ProductPageViewModelState<ProductItemModel>> get() = _loadingState


    private val mockItem = ProductItemModel(
        category = "",
        description = "",
        id = 0,
        rating = Rating(0, 0.0),
        title = "",
        price = 0.0,
        image = "Some path"
    )

    fun saveFavorite(item: ProductItemModel) {
        viewModelScope.launch {
            putProductToDatabaseUseCase(mapper.toProductItem(item))
        }
    }

    fun getProductPageInformation(id: Int) {
        viewModelScope.launch {
            _loadingState.value =
                ProductPageViewModelState(
                    mockItem,
                    View.VISIBLE,
                    View.GONE,
                    R.drawable.ic_favorites_unchecked,
                    false
                )
            getSingleProductUseCase(id).collect { result ->
                result
                    .onSuccess { product ->
                        val resourceImg = if (isAdded(product.id)) {
                            R.drawable.ic_favorites_checked
                        } else {
                            R.drawable.ic_favorites_unchecked
                        }
                        val isFavorite = isAdded(product.id)
                        _loadingState.value = ProductPageViewModelState(
                            product,
                            loadingVisibility = View.GONE,
                            heartButtonVisibility = View.VISIBLE,
                            resourceImg,
                            isFavorite
                        )
                    }
                    .onFailure { exception ->
                        _errorAction.value = Events.Error(
                            exception.message ?: "Error with product loading", View.GONE
                        )
                    }

            }
        }
    }

    private suspend fun isAdded(id: Int): Boolean {
        var list = listOf<ProductItem>()
        // Creating list for items id
        val listOfIds = mutableListOf<Int>()
        viewModelScope.launch {
            getSavedProductsFromDatabaseUseCase().collect { listProducts ->
                list = listProducts
            }
        }
        list.forEach {
            listOfIds.add(it.id)
        }

        return listOfIds.contains(id)
    }

    fun deleteFromFavorites(item: ProductItemModel) {
        viewModelScope.launch {
            deleteProductFromDatabaseUseCase(mapper.toProductItem(item))
        }
    }

    data class ProductPageViewModelState<T>(
        val item: T,
        val loadingVisibility: Int,
        val heartButtonVisibility: Int,
        @DrawableRes val resourceImg: Int,
        val isFavorite: Boolean
    )
}