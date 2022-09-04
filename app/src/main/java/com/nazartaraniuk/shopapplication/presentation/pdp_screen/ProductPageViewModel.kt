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
import com.nazartaraniuk.shopapplication.domain.usecases.*
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.SingleLiveEvent
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductPageViewModel @Inject constructor(
    private val getSingleProductUseCase: GetSingleProductUseCase,
    private val putProductToDatabaseUseCase: PutProductToDatabaseUseCase,
    private val checkIsAddedUseCase: CheckIsAddedUseCase,
    private val deleteProductFromDatabaseUseCase: DeleteProductFromDatabaseUseCase,
    private val mapper: ToUiModelMapper,
    private val dispatcher: CoroutineDispatcher,
    private val buyProductUseCase: BuyProductUseCase
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
        viewModelScope.launch(dispatcher) {
            putProductToDatabaseUseCase(mapper.toProductItem(item))
        }
    }

    fun buyProduct(item: ProductItemModel) {
        viewModelScope.launch {
            buyProductUseCase(mapper.toProductItem(item))
        }
    }

    fun getProductPageInformation(id: Int) {
        viewModelScope.launch(dispatcher) {
            _loadingState.postValue(
                ProductPageViewModelState(
                    mockItem,
                    View.VISIBLE,
                    View.GONE,
                    Events.UnSaved(R.drawable.ic_favorites_unchecked),
                    false
                )
            )
            getSingleProductUseCase(id).collect { result ->
                result
                    .onSuccess { product ->
                        val isAdded = checkIsAddedUseCase(product.id)
                        val resourceImg = if (isAdded) {
                            Events.Saved(R.drawable.ic_favorites_checked)
                        } else {
                            Events.UnSaved(R.drawable.ic_favorites_unchecked)
                        }
                        _loadingState.postValue(ProductPageViewModelState(
                            product,
                            loadingVisibility = View.GONE,
                            heartButtonVisibility = View.VISIBLE,
                            resourceImg,
                            isAdded
                        ))
                    }
                    .onFailure { exception ->
                        _errorAction.postValue(Events.Error(
                            exception.message ?: "Error with product loading", View.GONE
                        ))
                    }

            }
        }
    }

    fun deleteFromFavorites(item: ProductItemModel) {
        viewModelScope.launch(dispatcher) {
            deleteProductFromDatabaseUseCase(mapper.toProductItem(item))
        }
    }

    data class ProductPageViewModelState<T>(
        val item: T,
        val loadingVisibility: Int,
        val heartButtonVisibility: Int,
        @DrawableRes val resourceImg: Events,
        val isFavorite: Boolean
    )
}