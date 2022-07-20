package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException
import kotlin.reflect.full.IllegalPropertyDelegateAccessException

class AdapterDelegatesManager<T>(vararg delegates: AdapterDelegate<T>) {

    private val delegates: SparseArrayCompat<AdapterDelegate<T>> = SparseArrayCompat()

    init {
        for (element in delegates) {
            addDelegate(element)
        }
    }

    init {
        for (element in delegates) {
            addDelegate(element)
        }
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegate: AdapterDelegate<T> = delegates[viewType] ?: error("Delegate is null!")
        return delegate.onCreateViewHolder(parent)
    }

    fun onBindViewHolder(items: List<T>, position: Int, holder: RecyclerView.ViewHolder) {
        val delegate: AdapterDelegate<T> = delegates[holder.itemViewType] ?: error("Delegate is null!")
        delegate.onBindViewHolder(holder, items, position)
    }

    private fun addDelegate(delegate: AdapterDelegate<T>) {
        var viewType = delegates.size()
        while (delegates[viewType] != null) {
            viewType++
        }
        delegates.put(viewType, delegate)
    }

    fun getItemViewType(items: List<T>, position: Int): Int {
        val delegatesCount = delegates.size()
        for (i in 0 until delegatesCount) {
            val delegate: AdapterDelegate<T> = delegates.valueAt(i)
            if (delegate.isForViewType(items, position)) {
                return delegates.keyAt(i)
            }
        }
        error("Cannot find ")
    }
}