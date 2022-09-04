package com.nazartaraniuk.shopapplication.presentation.favorites_screen

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaraniuk.shopapplication.domain.usecases.GetSavedProductsFromDatabaseUseCase
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.SingleLiveEvent
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesFragmentViewModel @Inject constructor(
    private val getSavedProductsFromDatabaseUseCase: GetSavedProductsFromDatabaseUseCase,
    private val mapper: ToUiModelMapper
) : ViewModel() {

    private val _errorAction = SingleLiveEvent<Events>()
    val errorAction: LiveData<Events> get() = _errorAction

    private val _loadingState = MutableLiveData<FavoritesViewModelState<DisplayableItem>>()
    val loadingState: LiveData<FavoritesViewModelState<DisplayableItem>> get() = _loadingState

    fun getItemsFromDatabase() {
        viewModelScope.launch {
            _loadingState.value = FavoritesViewModelState(listOf(), true)
            getSavedProductsFromDatabaseUseCase().collect { list ->
                if (list.isNotEmpty()) {
                    val productModelList = list.map { item ->
                        mapper.toProductItemModel(item)
                    }
                    _loadingState.value = FavoritesViewModelState(productModelList, false)
                } else {
                    _errorAction.value = Events.Error(ERROR_MESSAGE, false)
                }
            }
        }
    }

    data class FavoritesViewModelState<T>(val items: List<T>, val isVisible: Boolean)

    companion object {
        const val ERROR_MESSAGE = "You haven't added to favorites yet"
    }
}