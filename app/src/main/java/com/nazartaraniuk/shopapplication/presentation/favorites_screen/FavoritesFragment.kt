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
import com.nazartaraniuk.shopapplication.presentation.adapters.*
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.createErrorSnackBar
import com.nazartaraniuk.shopapplication.presentation.common.setAdapter
import com.nazartaraniuk.shopapplication.presentation.di.FavoriteSubcomponent
import com.nazartaraniuk.shopapplication.presentation.di.MainApplication
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject
    lateinit var viewModel: FavoritesFragmentViewModel
    private lateinit var favoriteSubcomponent: FavoriteSubcomponent
    private var binding: FragmentFavoritesBinding? = null
    private val adapterManager = AdapterDelegatesManager(
        ProductItemAdapterDelegate(),
        ProductsListGridAdapterDelegate(),
    )
    private val adapter = DelegationAdapter(
        adapterManager
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpComponent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
        viewModel.getItemsFromDatabase()
        val isTablet = this.resources.getBoolean(R.bool.isTablet)
        val spanCount = if (isTablet) SPAN_COUNT_TABLET else SPAN_COUNT_MOBILE
        binding?.rvFavouritesList?.let { recyclerView ->
            setAdapter(
                recyclerView,
                adapter,
                GridLayoutManager(requireActivity(), spanCount)
            )
        }
    }

    private fun setUpComponent() {
        favoriteSubcomponent =
            (requireActivity().application as MainApplication)
                .appComponent
                .favoritesSubcomponent()
                .build()
        favoriteSubcomponent.inject(this)
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
                else -> {}
            }
        }

        loadingState.observe(viewLifecycleOwner) {
            adapter.setItems(it.items)
        }
    }

    companion object {
        const val SPAN_COUNT_TABLET = 4
        const val SPAN_COUNT_MOBILE = 2
    }
}