package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.CategoryItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel

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
                ELECTRONICS -> loadImage(binding.ivCategoryIcon, R.drawable.ic_electronics)
                JEWELERY -> loadImage(binding.ivCategoryIcon, R.drawable.ic_jewerly)
                MENS_CLOTHING -> loadImage(binding.ivCategoryIcon, R.drawable.ic_men_clothing)
                WOMENS_CLOTHING -> loadImage(binding.ivCategoryIcon, R.drawable.ic_women_clothing)
            }
        }

        private fun loadImage(imageView: ImageView, image: Int) {
            Glide.with(binding.root.context)
                .load(image)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }
    }
    companion object {
        const val ELECTRONICS = "Electronics"
        const val JEWELERY = "Jewelery"
        const val MENS_CLOTHING = "Men's clothing"
        const val WOMENS_CLOTHING = "Women's clothing"
    }
}