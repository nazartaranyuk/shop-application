package com.nazartaraniuk.shopapplication.presentation.adapters

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.forEach
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.CategoriesListItemBinding
import com.nazartaraniuk.shopapplication.databinding.CategoryItemBinding
import com.nazartaraniuk.shopapplication.databinding.CategorySmallItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.CategoriesSmallListModel

class CategoriesListSmallAdapterDelegate :
    AdapterDelegate<DisplayableItem> {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            CategoriesListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is CategoriesSmallListModel
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as CategoryViewHolder).bind(items[position] as CategoriesSmallListModel)
    }

    inner class CategoryViewHolder(private val binding: CategoriesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val categoryItemBinding = CategorySmallItemBinding.inflate(LayoutInflater.from(binding.root.context))

        fun bind(model: CategoriesSmallListModel) {
            binding.root.addView(categoryItemBinding.root)
        }
    }
}