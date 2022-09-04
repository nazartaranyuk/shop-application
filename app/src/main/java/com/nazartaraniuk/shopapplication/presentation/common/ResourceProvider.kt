package com.nazartaraniuk.shopapplication.presentation.common

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(
    private val context: Context
) {

    fun getStringFromResources(@StringRes id: Int): String {
        return context.resources.getString(id)
    }
}