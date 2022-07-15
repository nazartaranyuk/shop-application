package com.nazartaraniuk.shopapplication.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaraniuk.shopapplication.domain.common.NetworkResult
import com.nazartaraniuk.shopapplication.domain.usecases.GetCategoriesUseCase
import com.nazartaraniuk.shopapplication.presentation.Events
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    val updateList = MutableLiveData<Events>()

    init {
        viewModelScope.launch {
            getCategoriesUseCase().collect {
                updateList.value = when(it) {
                    is NetworkResult.Loading -> Events.Loading
                    is NetworkResult.Success -> Events.Success(it.data)
                    is NetworkResult.Error -> Events.Error("Error!!")
                }
            }
        }
    }

}