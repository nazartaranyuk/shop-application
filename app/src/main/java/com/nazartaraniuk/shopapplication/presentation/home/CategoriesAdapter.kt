package com.nazartaraniuk.shopapplication.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.CategoryItemBinding
import com.squareup.picasso.Picasso

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private val listOfCategories = mutableListOf<String>()

    fun updateList(list: List<String>) {
        listOfCategories.clear()
        listOfCategories.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) = holder.bind(listOfCategories[position])

    override fun getItemCount(): Int = listOfCategories.size

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(element: String) {
            binding.tvCategoryDescription.text = element

            when(listOfCategories[adapterPosition]) {
                "electronics" -> loadImage(binding.ivCategoryItem, R.drawable.electronics)
                "jewelery" -> loadImage(binding.ivCategoryItem, R.drawable.jewerly)
                "men's clothing" -> loadImage(binding.ivCategoryItem, R.drawable.mensclothing)
                "women's clothing" -> loadImage(binding.ivCategoryItem, R.drawable.womensclothing)
            }
        }

        private fun loadImage(imageView: ImageView, @DrawableRes image: Int) {
            Picasso.get()
                .load(image)
                .centerCrop()
                .into(imageView)
        }
    }
}