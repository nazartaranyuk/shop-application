package com.nazartaraniuk.shopapplication.presentation.home

import androidx.lifecycle.*
import com.nazartaraniuk.shopapplication.domain.usecases.GetHomePageUseCase
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.SingleLiveEvent
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    private val getHomePageUseCase: GetHomePageUseCase
) : ViewModel() {

    private val _errorAction = SingleLiveEvent<Events<List<DisplayableItem>>>()
    val errorAction: LiveData<Events<List<DisplayableItem>>> get() = _errorAction

    private val _loadingState = MutableLiveData<HomeViewModelState<DisplayableItem>>()
    val loadingState: LiveData<HomeViewModelState<DisplayableItem>> get() = _loadingState

    init {
        viewModelScope.launch {
            _loadingState.value = HomeViewModelState(listOf(), true)
            getHomePageUseCase().collect { result ->
                result
                    .onSuccess { list ->
                        _loadingState.value = HomeViewModelState(list, false)
                    }
                    .onFailure { exception ->
                        _errorAction.value =
                            Events.Error(exception.message ?: "Some error")
                    }
            }
        }
    }

    data class HomeViewModelState<T>(val items: List<T>, val isLoading: Boolean)
}
