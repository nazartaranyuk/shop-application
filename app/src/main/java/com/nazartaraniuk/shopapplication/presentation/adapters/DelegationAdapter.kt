package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class DelegationAdapter<T> (
    private val delegatesManager: AdapterDelegatesManager<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val items = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return delegatesManager.onBindViewHolder(items, position, holder)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(items, position)
    }

    fun setItems(newList: List<T>) {
        items.apply {
            addAll(newList)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}