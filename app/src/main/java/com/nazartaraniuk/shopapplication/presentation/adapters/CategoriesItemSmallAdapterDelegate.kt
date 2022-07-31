package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.CategoryItemBinding
import com.nazartaraniuk.shopapplication.databinding.CategorySmallItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.squareup.picasso.Picasso

class CategoriesItemSmallAdapterDelegate : AdapterDelegate<DisplayableItem> {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            CategorySmallItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is CategoryItemModel
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as CategoryViewHolder).bind(items[position] as CategoryItemModel)
    }


    inner class CategoryViewHolder(
        private val binding: CategorySmallItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(element: CategoryItemModel) {
            binding.tvCategorySmallDescription.text = element.category
        }
    }
}