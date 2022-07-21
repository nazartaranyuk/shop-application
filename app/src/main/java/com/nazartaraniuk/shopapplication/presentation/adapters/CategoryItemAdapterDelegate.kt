package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.CategoryItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.squareup.picasso.Picasso
import java.util.*

class CategoryItemAdapterDelegate : AdapterDelegate<DisplayableItem> {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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


    inner class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(element: CategoryItemModel) {
            binding.tvCategoryDescription.text = element.category.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }

            when(element.category) {
                "electronics" -> loadImage(binding.ivCategoryItem, R.drawable.electronics)
                "jewelery" -> loadImage(binding.ivCategoryItem, R.drawable.jewerly)
                "men's clothing" -> loadImage(binding.ivCategoryItem, R.drawable.mensclothing)
                "women's clothing" -> loadImage(binding.ivCategoryItem, R.drawable.womensclothing)
            }
        }

        private fun loadImage(imageView: ImageView, @DrawableRes image: Int) {
            Picasso.get()
                .load(image)
                .fit()
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }
    }
}