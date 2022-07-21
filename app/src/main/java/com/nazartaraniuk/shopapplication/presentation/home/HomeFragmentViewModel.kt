package com.nazartaraniuk.shopapplication.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaraniuk.shopapplication.domain.common.NetworkResult
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.usecases.GetCategoriesUseCase
import com.nazartaraniuk.shopapplication.domain.usecases.GetTrendingProductsUseCase
import com.nazartaraniuk.shopapplication.presentation.Events
import com.nazartaraniuk.shopapplication.presentation.ToUiModelMapper
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getTrendingProductsUseCase: GetTrendingProductsUseCase
) : ViewModel() {

    private val _updateList = MutableLiveData<Events>()
    val updateList: LiveData<Events> get() = _updateList

    private val _updateTrendingList = MutableLiveData<Events>()
    val updateTrendingList: LiveData<Events> get() = _updateTrendingList

    init {
        viewModelScope.launch {
            getCategoriesUseCase().collect {
                _updateList.value = when(it) {
                    is NetworkResult.Loading -> Events.Loading
                    is NetworkResult.Success -> Events.Success(it.data?.map { name ->
                        ToUiModelMapper.toCategoryItemModel(name)
                    })
                    is NetworkResult.Error -> Events.Error("Error!!")
                }
            }
            getTrendingProductsUseCase().collect {
                _updateTrendingList.value = when(it) {
                    is NetworkResult.Loading -> Events.Loading
                    is NetworkResult.Success -> Events.Success(createTrendingList(it.data))
                    is NetworkResult.Error -> Events.Error("Error!!")
                }
            }
        }
    }

    private fun createTrendingList(list: List<ProductItem>?) : List<ProductItemModel> {
        val newList = mutableListOf<ProductItemModel>()
        // Here I sort the entry list by rating and put the first five values into a new list
        list?.sortedBy { it.rating.rate }
        var i = 0
        while(i < 5) {
            val tempItem = ToUiModelMapper.toProductItemModel(list?.get(i) ?: throw IllegalAccessError())
            newList.add(tempItem)
            i++
        }
        return newList
    }

}