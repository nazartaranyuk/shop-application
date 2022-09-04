package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.TrendingListItemBinding
import com.nazartaraniuk.shopapplication.presentation.common.buttonAnimation
import com.nazartaraniuk.shopapplication.presentation.home_screen.HomeFragment
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import com.nazartaraniuk.shopapplication.presentation.models.ProductListModel

class TrendingListAdapterDelegate(
    private val openProductPage: (View, ProductItemModel) -> Unit
):
    AdapterDelegate<DisplayableItem>() {

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
        model: DisplayableItem,
        payloads: List<DisplayableItem.Payloadable>
    ) {
        (holder as TrendingViewHolder).bind(model as ProductListModel)
    }


    inner class TrendingViewHolder(private val binding: TrendingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ProductListModel) {

            val adapterManager = AdapterDelegatesManager(
                TrendingItemAdapterDelegate(openProductPage)
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