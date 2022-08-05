package com.nazartaraniuk.shopapplication.presentation.common

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.FragmentProductPageBinding
import com.nazartaraniuk.shopapplication.databinding.SnackbarErrorBinding
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import com.squareup.picasso.Picasso
import java.util.*

fun <R : RecyclerView.ViewHolder?> setAdapter(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<R>,
    layoutManager: RecyclerView.LayoutManager
) {
    recyclerView.adapter = adapter
    recyclerView.layoutManager = layoutManager
}

fun firstLetterToUpperCase(list: List<CategoryItemModel>) : List<CategoryItemModel> {
    return list.map { categoryItemModel ->
        categoryItemModel.copy(category = categoryItemModel.category.replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase(Locale.getDefault())
            } else {
                it.toString()
            }
        })
    }
}

fun createErrorSnackBar(view: View, layoutInflater: LayoutInflater, text: String) {
    val binding = SnackbarErrorBinding.inflate(layoutInflater)
    binding.tvSnackbarError.text = text
    Snackbar.make(view, "", Snackbar.LENGTH_LONG).apply {
        (this.view as SnackbarLayout).apply {
            addView(binding.root, 0)
            setPadding(0, 0, 0, 0)
            setBackgroundColor(Color.TRANSPARENT)
        }.also {
            show()
        }
    }
}

private fun loadImage(imageView: ImageView, imageUrl: String) {
    Picasso.get()
        .load(imageUrl)
        .fit()
        .error(R.drawable.ic_launcher_foreground)
        .into(imageView)
}

fun setUpInterface(model: ProductItemModel, binding: FragmentProductPageBinding) = with(binding) {
    binding.tvProductItemCategory.text = model.category
    binding.tvProductItemName.text = model.title
    binding.tvProductItemPrice.text = "${model.price} USD"
    binding.tvProductItemDescription.text = model.description
    binding.rbProductItemRating.rating = model.rating.rate.toFloat()
    loadImage(binding.ivProductImage, model.image)
}