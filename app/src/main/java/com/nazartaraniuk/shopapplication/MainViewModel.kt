package com.nazartaraniuk.shopapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazartaraniuk.shopapplication.presentation.common.SharedPreferencesHelper
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {
    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean> get() = _isChecked

    init {
        _isChecked.value = sharedPreferencesHelper.getFromPreference()
    }

    fun saveSwitchState(boolean: Boolean) {
        sharedPreferencesHelper.putInPreference(boolean)
        _isChecked.value = boolean
    }
}