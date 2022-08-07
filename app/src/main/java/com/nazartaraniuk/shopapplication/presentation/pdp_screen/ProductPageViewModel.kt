package com.nazartaraniuk.shopapplication.presentation.pdp_screen

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaraniuk.shopapplication.domain.entities.Rating
import com.nazartaraniuk.shopapplication.domain.usecases.GetSingleProductUseCase
import com.nazartaraniuk.shopapplication.domain.usecases.PutProductToDatabaseUseCase
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.SingleLiveEvent
import com.nazartaraniuk.shopapplication.presentation.explore_screen.ExploreFragmentViewModel
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductPageViewModel @Inject constructor(
    private val getSingleProductUseCase: GetSingleProductUseCase,
    private val putProductToDatabaseUseCase: PutProductToDatabaseUseCase,
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
            _loadingState.value = ProductPageViewModelState(mockItem, View.VISIBLE)
            getSingleProductUseCase(id).collect { result ->
                result
                    .onSuccess { product ->
                        _loadingState.value = ProductPageViewModelState(product, View.GONE)
                    }
                    .onFailure { exception ->
                        _errorAction.value = Events.Error(
                            exception.message ?: "Error with product loading", View.GONE
                        )
                    }

            }
        }
    }

    data class ProductPageViewModelState<T>(val item: T, val visibility: Int)
}