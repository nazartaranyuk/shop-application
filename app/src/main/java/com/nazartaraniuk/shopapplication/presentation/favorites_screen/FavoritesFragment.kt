package com.nazartaraniuk.shopapplication.presentation.favorites_screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.FragmentFavoritesBinding
import com.nazartaraniuk.shopapplication.presentation.adapters.AdapterDelegatesManager
import com.nazartaraniuk.shopapplication.presentation.adapters.DelegationAdapter
import com.nazartaraniuk.shopapplication.presentation.adapters.ProductsListGridAdapterDelegate
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.createErrorSnackBar
import com.nazartaraniuk.shopapplication.presentation.common.setAdapter
import com.nazartaraniuk.shopapplication.presentation.di.getComponent
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    @Inject
    lateinit var viewModel: FavoritesFragmentViewModel

    private val adapterManager = AdapterDelegatesManager(
        ProductsListGridAdapterDelegate()
    )
    private val adapter = DelegationAdapter(
        adapterManager
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().application.getComponent().inject(this)
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
        subscribeToLiveData()
        setAdapter(
            binding.rvFavouritesList,
            adapter,
            GridLayoutManager(requireActivity(), 2)
        )
    }

    private fun subscribeToLiveData() = with(viewModel) {
        errorAction.observe(viewLifecycleOwner) {
            when (it) {
                is Events.Error -> {
                    adapter.setItems(emptyList())
                    createErrorSnackBar(
                        requireView(),
                        layoutInflater,
                        it.message
                    )
                }
            }
        }

        loadingState.observe(viewLifecycleOwner) {
            adapter.setItems(it.items)
        }
    }

}