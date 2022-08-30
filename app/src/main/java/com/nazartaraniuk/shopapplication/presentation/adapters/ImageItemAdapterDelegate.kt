package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.databinding.ImageItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.ImageItemModel

class ImageItemAdapterDelegate : AdapterDelegate<DisplayableItem>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ItemImageViewHolder(
            ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is ImageItemModel
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        model: DisplayableItem,
        payloads: List<Any>
    ) {
        (holder as ItemImageViewHolder).binding.ivMainImage
    }

    inner class ItemImageViewHolder(val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)
}