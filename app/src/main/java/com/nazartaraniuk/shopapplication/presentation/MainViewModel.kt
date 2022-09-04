package com.nazartaraniuk.shopapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazartaraniuk.shopapplication.presentation.notifications.NotificationHelper
import com.nazartaraniuk.shopapplication.presentation.common.SharedPreferencesHelper
import com.nazartaraniuk.shopapplication.presentation.common.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val notificationHelper: NotificationHelper
) : ViewModel() {

    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean> get() = _isChecked

    private val _isOnCheckedAction = SingleLiveEvent<Unit>()
    val isOnCheckedAction: LiveData<Unit> get() = _isOnCheckedAction


    init {
        _isChecked.value = sharedPreferencesHelper.getFromPreference()
    }

    private fun saveSwitchState(boolean: Boolean) {
        sharedPreferencesHelper.putInPreference(boolean)
        _isChecked.value = boolean
    }

    fun startShowingNotifications() {
        if (sharedPreferencesHelper.getFromPreference()) {
            notificationHelper.startSendingNotifications()
        }
    }

    fun shouldShowNotification(isChecked: Boolean) {
        saveSwitchState(isChecked)
        if (isChecked) {
            notificationHelper.startSendingNotifications()
            _isOnCheckedAction.call()
        } else {
            notificationHelper.stopSendingNotifications()
        }

    }
}