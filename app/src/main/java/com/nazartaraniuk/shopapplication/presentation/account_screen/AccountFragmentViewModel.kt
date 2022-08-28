package com.nazartaraniuk.shopapplication.presentation.account_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazartaraniuk.shopapplication.presentation.common.SharedPreferencesHelper
import com.nazartaraniuk.shopapplication.presentation.common.SingleLiveEvent
import javax.inject.Inject

class AccountFragmentViewModel @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean> get() = _isChecked

    private val _isOnCheckedAction = SingleLiveEvent<Unit>()
    val isOnCheckedAction: LiveData<Unit> get() = _isOnCheckedAction

    private val _isOnUncheckedAction = SingleLiveEvent<Unit>()
    val isOnUncheckedAction: LiveData<Unit> get() = _isOnCheckedAction

    init {
        _isChecked.value = sharedPreferencesHelper.getFromPreference()
    }

    fun shouldShowNotification(isChecked: Boolean) {
        saveSwitchState(isChecked)
        if (isChecked) {
            _isOnCheckedAction.call()
        } else {
            _isOnUncheckedAction.call()
        }

    }

    fun saveSwitchState(boolean: Boolean) {
        sharedPreferencesHelper.putInPreference(boolean)
        _isChecked.value = boolean
    }
}