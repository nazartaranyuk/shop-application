package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AdapterDelegate<T> {

    abstract fun isForViewType(items: List<T>, position: Int) : Boolean

    abstract fun onCreateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder

    abstract fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        model: T,
        payloads: List<DisplayableItem.Payloadable>
    )

}