package com.nazartaraniuk.shopapplication.presentation.explore_screen

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaraniuk.shopapplication.domain.usecases.GetExplorePageUseCase
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.ExploreFragmentUIComposer
import com.nazartaraniuk.shopapplication.presentation.common.SingleLiveEvent
import com.nazartaraniuk.shopapplication.presentation.models.CategoriesSmallListModel
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import com.nazartaraniuk.shopapplication.presentation.models.ProductListModel
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
            _loadingState.value = ExploreViewModelState(listOf(), listOf(), View.VISIBLE)
            getExplorePageUseCase().collect { result ->
                result
                    .onSuccess { pair ->
                        categoryProductPair = pair
                        val list = composer.composeInterface(
                            firstList = pair.first,
                            secondList = pair.second,
                            currentSelectedCategory = currentCategory,
                        )

                        _loadingState.value = ExploreViewModelState(pair.first, pair.second, View.GONE)
                    }
                    .onFailure { exception ->
                        _errorAction.value =
                            Events.Error(exception.message ?: "Unknown error", View.GONE)
                    }
            }
        }
    }

    fun onCategoryClicked(category: String) {
        currentCategory = category
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.value?.let {
                val categoryItems = categoryProductPair?.first.orEmpty()
                val productItems = categoryProductPair?.second.orEmpty()

                val categories = composer.composeCategoriesList(categoryItems, currentCategory)
                val products = composer.composeProductList(productItems, currentCategory)

                _loadingState.postValue(
                    _loadingState.value?.copy(
                        categories = categories.categories,
                        products = products.productItems
                    )
                )
            }
        }
    }

    data class ExploreViewModelState<T>(
        val categories: List<T>,
        val products: List<T>,
        val visibility: Int
    )
}
