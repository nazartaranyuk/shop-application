package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.CategoriesListItemBinding
import com.nazartaraniuk.shopapplication.databinding.CategoryItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.CategoryListModel

class CategoryListAdapterDelegate(
    private val openPage: (String) -> Unit
) : AdapterDelegate<DisplayableItem>(){

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            CategoriesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is CategoryListModel
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        model: DisplayableItem,
        payloads: List<DisplayableItem.Payloadable>
    ) {
        (holder as CategoryViewHolder).bind(model as CategoryListModel)
    }


    inner class CategoryViewHolder(private val binding: CategoriesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CategoryListModel) {
            binding.linearLayout.removeAllViews()

            model.categories.forEach { categoryModel ->

                val categoryBinding = CategoryItemBinding.inflate(
                    LayoutInflater.from(binding.root.context),
                    binding.root,
                    false
                )

                categoryBinding.root.setOnClickListener {
                    openPage(categoryModel.category)
                }

                categoryBinding.tvCategoryDescription.text = categoryModel.category
                when (categoryModel.category) {
                    ELECTRONICS -> loadImage(categoryBinding.ivCategoryIcon, R.drawable.ic_electronics)
                    JEWELERY -> loadImage(categoryBinding.ivCategoryIcon, R.drawable.ic_jewerly)
                    MENS_CLOTHING -> loadImage(categoryBinding.ivCategoryIcon, R.drawable.ic_men_clothing)
                    WOMENS_CLOTHING -> loadImage(
                        categoryBinding.ivCategoryIcon,
                        R.drawable.ic_women_clothing
                    )
                }
                binding.linearLayout.addView(categoryBinding.root)
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