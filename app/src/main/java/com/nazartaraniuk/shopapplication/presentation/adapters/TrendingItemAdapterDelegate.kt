package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.ProductItemBinding
import com.nazartaraniuk.shopapplication.databinding.TrendingProductItemBinding
import com.nazartaraniuk.shopapplication.presentation.common.buttonAnimation
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import com.nazartaraniuk.shopapplication.presentation.pdp_screen.ProductPageFragmentDirections
import com.squareup.picasso.Picasso
import java.util.*

class TrendingItemAdapterDelegate(
    private val openProductPage: (View, ProductItemModel) -> Unit
) : AdapterDelegate<DisplayableItem>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ProductViewHolder(
            TrendingProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is ProductItemModel
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        model: DisplayableItem,
        payloads: List<DisplayableItem.Payloadable>
    ) {
        (holder as ProductViewHolder).bind(model as ProductItemModel)
    }

    inner class ProductViewHolder(private val binding: TrendingProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ProductItemModel) {
            loadImage(binding.ivProductImage, model.image)
            binding.tvProductItemName.text = model.title
            binding.tvProductItemCategory.text = model.category
            binding.tvTrendingItemPrice.text = "${model.price} USD"

            binding.root.setOnClickListener { view ->
                openProductPage(view, model)
            }
        }

        private fun loadImage(imageView: ImageView, imageUrl: String) {
            Picasso.get()
                .load(imageUrl)
                .fit()
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }
    }
}