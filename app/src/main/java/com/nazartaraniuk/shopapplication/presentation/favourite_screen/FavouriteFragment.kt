package com.nazartaraniuk.shopapplication.presentation.favourite_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.nazartaraniuk.shopapplication.databinding.FragmentFavoritesBinding
import com.nazartaraniuk.shopapplication.presentation.adapters.AdapterDelegatesManager
import com.nazartaraniuk.shopapplication.presentation.adapters.DelegationAdapter
import com.nazartaraniuk.shopapplication.presentation.adapters.ProductItemAdapterDelegate
import com.nazartaraniuk.shopapplication.presentation.common.setAdapter

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val adapterManager by lazy {
        AdapterDelegatesManager(
            ProductItemAdapterDelegate()
        )
    }
    private val adapter by lazy {
        DelegationAdapter(adapterManager)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter(
            binding.rvFavouritesList,
            adapter,
            GridLayoutManager(requireActivity(), 2)
        )
    }

}