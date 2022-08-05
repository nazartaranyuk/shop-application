package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.databinding.TrendingListItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.ProductListModel

class TrendingListAdapterDelegate:
    AdapterDelegate<DisplayableItem> {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TrendingViewHolder(
            TrendingListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is ProductListModel
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as TrendingViewHolder).bind(items[position] as ProductListModel)
    }


    inner class TrendingViewHolder(private val binding: TrendingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ProductListModel) {

            val adapterManager = AdapterDelegatesManager(
                ProductItemAdapterDelegate()
            )

            val adapter = DelegationAdapter(adapterManager)
            adapter.setItems(model.productItems)

            binding.rvTrendingList.adapter = adapter
            binding.rvTrendingList.layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }
}