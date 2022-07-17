package com.nazartaraniuk.shopapplication.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.TrendingProductItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import com.squareup.picasso.Picasso
import java.util.*

class TrendingListAdapter : RecyclerView.Adapter<TrendingListAdapter.TrendingViewHolder>() {

    private val listOfTrendingItems = mutableListOf<ProductItemModel>()

    fun updateList(list: List<ProductItemModel>) {
        listOfTrendingItems.clear()
        listOfTrendingItems.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(
            TrendingProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) = holder.bind(listOfTrendingItems[position])

    override fun getItemCount(): Int = listOfTrendingItems.size

    inner class TrendingViewHolder(private val binding: TrendingProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ProductItemModel) {
            loadImage(binding.ivTrendingProductImage, model.image)
            binding.tvTrendingItemName.text = model.title
            binding.tvTrendingItemPrice.text = model.price.toString()
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