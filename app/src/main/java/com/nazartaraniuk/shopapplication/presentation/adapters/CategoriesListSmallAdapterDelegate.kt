package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.CategoriesListItemBinding
import com.nazartaraniuk.shopapplication.databinding.CategorySmallItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.CategoriesSmallListModel

class CategoriesListSmallAdapterDelegate(
    private val clickListener: (String) -> Unit,
) : AdapterDelegate<DisplayableItem>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            CategoriesListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is CategoriesSmallListModel
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        model: DisplayableItem,
        payloads: List<DisplayableItem.Payloadable>
    ) {
        if (payloads.isEmpty()) {
            (holder as CategoryViewHolder).bind(model as CategoriesSmallListModel)
        } else {
            (holder as CategoryViewHolder).particularBind(model as CategoriesSmallListModel)
        }
    }

    inner class CategoryViewHolder(private val binding: CategoriesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CategoriesSmallListModel) {
            binding.linearLayout.removeAllViews()
            model.categories.forEach { category ->
                val viewBinding =
                    CategorySmallItemBinding.inflate(
                        LayoutInflater.from(binding.root.context),
                        binding.root,
                        false
                    )
                        .apply {
                            tvCategorySmallDescription.text = category.category
                        }
                if (category.isSelected) {
                    viewBinding.root.setBackgroundResource(
                        R.drawable.small_category_frame_selected
                    )
                }
                viewBinding.root.setOnClickListener {
                    clickListener(category.category)
                }
                binding.linearLayout.addView(viewBinding.root)
            }
        }

        fun particularBind(model: CategoriesSmallListModel) {
            model.categories.forEach { category ->

                binding.linearLayout.children.forEach { childView ->
                    // Unselecting button
                    childView.setBackgroundResource(R.drawable.small_category_frame)
                    if (category.isSelected) {
                        childView.setBackgroundResource(
                            R.drawable.small_category_frame_selected
                        )
                    }
                }
            }
        }
    }
}