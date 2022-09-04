package com.nazartaraniuk.shopapplication.presentation.home_screen

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaraniuk.shopapplication.domain.usecases.GetHomePageUseCase
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    private val getHomePageUseCase: GetHomePageUseCase
) : ViewModel() {

    private val _errorAction = SingleLiveEvent<Events>()
    val errorAction: LiveData<Events> get() = _errorAction

    private val _loadingState = MutableLiveData<HomeViewModelState<DisplayableItem>>()
    val loadingState: LiveData<HomeViewModelState<DisplayableItem>> get() = _loadingState

    init {
        viewModelScope.launch {
            _loadingState.value = HomeViewModelState(listOf(),
                animationVisibility = true,
                interfaceVisibility = false
            )
            getHomePageUseCase().collect { result ->
                result
                    .onSuccess { list ->
                        _loadingState.value = HomeViewModelState(list,
                            animationVisibility = false,
                            interfaceVisibility = true
                        )
                    }
                    .onFailure { exception ->
                        _errorAction.value =
                            Events.Error(exception.message ?: ERROR_MESSAGE, false)
                    }
            }
        }
    }

    data class HomeViewModelState<T>(
        val items: List<T>,
        val animationVisibility: Boolean,
        val interfaceVisibility: Boolean
    )
    companion object {
        const val ERROR_MESSAGE = "Some error"
    }
}
