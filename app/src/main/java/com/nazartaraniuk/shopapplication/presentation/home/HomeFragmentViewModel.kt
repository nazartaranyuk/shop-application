package com.nazartaraniuk.shopapplication.presentation.home

import androidx.lifecycle.*
import com.nazartaraniuk.shopapplication.domain.common.NetworkResult
import com.nazartaraniuk.shopapplication.domain.usecases.GetComposeInterfaceUseCase
import com.nazartaraniuk.shopapplication.presentation.Events
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    private val getComposeInterfaceUseCase: GetComposeInterfaceUseCase
) : ViewModel() {

    private val _updateList = MutableLiveData<Events<List<DisplayableItem>>>()
    val updateList: LiveData<Events<List<DisplayableItem>>> get() = _updateList

    init {
        viewModelScope.launch {
            getComposeInterfaceUseCase().collect {
                _updateList.value = when(it) {
                    is NetworkResult.Loading -> Events.Loading()
                    is NetworkResult.Success -> Events.Success(it.data ?: emptyList())
                    is NetworkResult.Error -> Events.Error(it.message ?: "Some error")
                }
            }
        }
    }


}