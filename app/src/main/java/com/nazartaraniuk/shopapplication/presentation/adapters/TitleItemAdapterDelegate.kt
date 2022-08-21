package com.nazartaraniuk.shopapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.databinding.TitleItemBinding
import com.nazartaraniuk.shopapplication.presentation.models.TitleItemModel

class TitleItemAdapterDelegate(
    private val navigateToPage: (Int) -> Unit
) : AdapterDelegate<DisplayableItem> {

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is TitleItemModel
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TitleItemViewHolder(
            TitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<DisplayableItem>,
        position: Int
    ) {
        (holder as TitleItemViewHolder).bind(items[position] as TitleItemModel)
    }

    inner class TitleItemViewHolder(private val binding: TitleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: TitleItemModel) = with(binding) {
            tvTrendingNow.text = model.title
            tvTrendingSeeAll.setOnClickListener {
                navigateToPage(model.link)
            }
        }
    }
}