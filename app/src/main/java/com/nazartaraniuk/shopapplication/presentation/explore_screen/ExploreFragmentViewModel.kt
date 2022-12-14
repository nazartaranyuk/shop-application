package com.nazartaraniuk.shopapplication.presentation.explore_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaraniuk.shopapplication.domain.usecases.GetExplorePageUseCase
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.ExploreFragmentUIComposer
import com.nazartaraniuk.shopapplication.presentation.common.SingleLiveEvent
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExploreFragmentViewModel @Inject constructor(
    private val getExplorePageUseCase: GetExplorePageUseCase,
    private val composer: ExploreFragmentUIComposer,
) : ViewModel() {

    private val _errorAction = SingleLiveEvent<Events>()
    val errorAction: LiveData<Events> get() = _errorAction

    private val _loadingState = MutableLiveData<ExploreViewModelState<DisplayableItem>>()
    val loadingState: LiveData<ExploreViewModelState<DisplayableItem>> get() = _loadingState

    private var currentCategory = ""

    private var categoryProductPair: Pair<List<CategoryItemModel>, List<ProductItemModel>>? = null

    init {
        viewModelScope.launch {
            _loadingState.value = ExploreViewModelState(listOf(), listOf(), isProgressBarVisible = true)
            getExplorePageUseCase().collect { result ->
                result
                    .onSuccess { pair ->
                        categoryProductPair = pair
                        val categories = composer.composeCategoriesList(pair.first, currentCategory)
                        val products = composer.composeProductList(pair.second, currentCategory)

                        _loadingState.value = ExploreViewModelState(
                            categories = categories.categories,
                            products = products.productItems,
                            isProgressBarVisible = false,
                        )
                    }
                    .onFailure { exception ->
                        _errorAction.value =
                            Events.Error(exception.message ?: UNKNOWN_ERROR)
                    }
            }
        }
    }

    fun onCategoryClicked(category: String) {
        currentCategory = category
        _loadingState.value = _loadingState.value?.copy(
            products = listOf(),
            isProgressBarVisible = true,
        )

        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.value?.let {
                val categoryItems = categoryProductPair?.first.orEmpty()
                val productItems = categoryProductPair?.second.orEmpty()

                val categories = composer.composeCategoriesList(categoryItems, currentCategory)
                val products = composer.composeProductList(productItems, currentCategory)

                _loadingState.postValue(
                    _loadingState.value?.copy(
                        categories = categories.categories,
                        products = products.productItems,
                        isProgressBarVisible = false,
                    )
                )
            }
        }
    }

    data class ExploreViewModelState<T>(
        val categories: List<T>,
        val products: List<T>,
        val isProgressBarVisible: Boolean = false,
    )

    companion object {
        const val UNKNOWN_ERROR = "Unknown error"
    }
}
