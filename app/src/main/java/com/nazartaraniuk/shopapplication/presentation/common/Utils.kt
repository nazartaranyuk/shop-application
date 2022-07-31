package com.nazartaraniuk.shopapplication.presentation.common

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.nazartaraniuk.shopapplication.databinding.SnackbarErrorBinding
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
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