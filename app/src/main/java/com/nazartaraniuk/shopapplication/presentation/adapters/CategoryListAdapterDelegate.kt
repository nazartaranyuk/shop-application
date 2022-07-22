package com.nazartaraniuk.shopapplication.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.CategoriesListItemBinding
import com.nazartaraniuk.shopapplication.databinding.CategoryItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.nazartaraniuk.shopapplication.presentation.models.CategoryListModel
import com.squareup.picasso.Picasso
import java.util.*

class CategoryListAdapterDelegate(
    val context: Context
) :
    AdapterDelegate<DisplayableItem> {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            CategoriesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is CategoryListModel
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as CategoryViewHolder).bind(items[position] as CategoryListModel)
    }


    inner class CategoryViewHolder(private val binding: CategoriesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CategoryListModel) {

            val adapterManager = AdapterDelegatesManager(
                CategoryItemAdapterDelegate()
            )

            val adapter = DelegationAdapter(adapterManager)
            adapter.setItems(model.categories)

            binding.rvCategoriesList.adapter = adapter
            binding.rvCategoriesList.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }
}