package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.databinding.CategoriesListItemBinding
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

        fun bind(model: CategoriesSmallListModel) {

            val adapterManager = AdapterDelegatesManager(
                CategoriesItemSmallAdapterDelegate()
            )

            val adapter = DelegationAdapter(adapterManager)
            adapter.setItems(model.categories)

            binding.rvCategoriesList.adapter = adapter
            binding.rvCategoriesList.layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }
}