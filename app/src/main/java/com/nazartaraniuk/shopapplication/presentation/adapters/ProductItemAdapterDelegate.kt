package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.TrendingProductItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import com.squareup.picasso.Picasso

class ProductItemAdapterDelegate : AdapterDelegate<DisplayableItem> {

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
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as ProductViewHolder).bind(items[position] as ProductItemModel)
    }

    inner class ProductViewHolder(private val binding: TrendingProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ProductItemModel) {
            loadImage(binding.ivTrendingProductImage, model.image)
            binding.tvTrendingItemName.text = model.title
            binding.tvTrendingItemPrice.text = "${model.price} USD"
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