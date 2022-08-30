package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.ProductListBinding
import com.nazartaraniuk.shopapplication.presentation.common.setAdapter
import com.nazartaraniuk.shopapplication.presentation.models.ProductListModel

class ProductsListGridAdapterDelegate : AdapterDelegate<DisplayableItem>() {

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is ProductListModel
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ProductsViewHolder(
            ProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        model: DisplayableItem,
        payloads: List<Any>
    ) {
        (holder as ProductsViewHolder).bind(model as ProductListModel)
    }

    inner class ProductsViewHolder(val binding: ProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ProductListModel) {
            val adapterManager = AdapterDelegatesManager(
                ProductItemAdapterDelegate()
            )
            val adapter = DelegationAdapter(adapterManager)
            adapter.setItems(model.productItems)
            val isTablet = binding.root.resources.getBoolean(R.bool.isTablet)
            if (isTablet) {
                setAdapter(
                    binding.rvProductList,
                    adapter,
                    GridLayoutManager(
                        binding.root.context,
                        4,
                    )
                )
            } else {
                setAdapter(
                    binding.rvProductList,
                    adapter,
                    GridLayoutManager(
                        binding.root.context,
                        2,
                    )
                )
            }
        }
    }
}