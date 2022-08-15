package com.nazartaraniuk.shopapplication.presentation.common

import android.content.Context
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(context: Context) {

    private val sharedPreferences by lazy { context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE) }

    fun putInPreference(boolean: Boolean) {
        sharedPreferences.edit().putBoolean(SHARED_KEY, boolean).apply()
    }

    fun getFromPreference() : Boolean {
        return sharedPreferences.getBoolean(SHARED_KEY, false)
    }

    companion object {
        const val SHARED_NAME = "Switch shared preferences"
        const val SHARED_KEY = "Switch key"
    }
}