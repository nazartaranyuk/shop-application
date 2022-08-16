package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.CategoryItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.squareup.picasso.Picasso

class CategoryItemAdapterDelegate : AdapterDelegate<DisplayableItem> {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is CategoryItemModel
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as CategoryViewHolder).bind(items[position] as CategoryItemModel)
    }


    inner class CategoryViewHolder(
        private val binding: CategoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(element: CategoryItemModel) {
            binding.tvCategoryDescription.text = element.category

            when (element.category) {
                // TODO move this magic words to companion object
                "Electronics" -> loadImage(binding.ivCategoryIcon, R.drawable.ic_electronics)
                "Jewelery" -> loadImage(binding.ivCategoryIcon, R.drawable.ic_jewerly)
                "Men's clothing" -> loadImage(binding.ivCategoryIcon, R.drawable.ic_men_clothing)
                "Women's clothing" -> loadImage(binding.ivCategoryIcon, R.drawable.ic_women_clothing)
            }
        }

        private fun loadImage(imageView: ImageView, image: Int) {
            Glide.with(binding.root.context)
                .load(image)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }
    }
}