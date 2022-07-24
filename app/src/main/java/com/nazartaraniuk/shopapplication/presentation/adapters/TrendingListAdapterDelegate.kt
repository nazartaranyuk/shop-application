package com.nazartaraniuk.shopapplication.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.databinding.CategoriesListItemBinding
import com.nazartaraniuk.shopapplication.databinding.TrendingListItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.CategoryListModel
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import com.nazartaraniuk.shopapplication.presentation.models.TrendingListModel

class TrendingListAdapterDelegate(
    val context: Context
) :
    AdapterDelegate<DisplayableItem> {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TrendingViewHolder(
            TrendingListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        (holder as TrendingViewHolder).bind(items[position] as TrendingListModel)
    }


    inner class TrendingViewHolder(private val binding: TrendingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: TrendingListModel) {

            val adapterManager = AdapterDelegatesManager(
                TrendingItemAdapterDelegate()
            )

            val adapter = DelegationAdapter(adapterManager)
            adapter.setItems(model.trendingItems)

            binding.rvTrendingList.adapter = adapter
            binding.rvTrendingList.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }
}