package com.nazartaraniuk.shopapplication.presentation.explore_screen

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaraniuk.shopapplication.domain.usecases.GetExplorePageUseCase
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExploreFragmentViewModel @Inject constructor(
    private val getExplorePageUseCase: GetExplorePageUseCase
) : ViewModel() {

    private val _errorAction = SingleLiveEvent<Events>()
    val errorAction: LiveData<Events> get() = _errorAction

    private val _loadingState = MutableLiveData<ExploreViewModelState<DisplayableItem>>()
    val loadingState: LiveData<ExploreViewModelState<DisplayableItem>> get() = _loadingState

    init {
        viewModelScope.launch {
            _loadingState.value = ExploreViewModelState(listOf(), View.VISIBLE)
            getExplorePageUseCase().collect { result ->
                result
                    .onSuccess { list ->
                        _loadingState.value = ExploreViewModelState(list, View.GONE)
                    }
                    .onFailure { exception ->
                        _errorAction.value =
                            Events.Error(exception.message ?: "Unknown error", View.GONE)
                    }
            }
        }
    }

    data class ExploreViewModelState<T>(val items: List<T>, val visibility: Int)
}