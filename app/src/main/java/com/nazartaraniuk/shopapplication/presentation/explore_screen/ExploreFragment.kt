package com.nazartaraniuk.shopapplication.presentation.explore_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nazartaraniuk.shopapplication.databinding.FragmentExploreBinding
import com.nazartaraniuk.shopapplication.presentation.adapters.*
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.createErrorSnackBar
import com.nazartaraniuk.shopapplication.presentation.common.setAdapter
import com.nazartaraniuk.shopapplication.presentation.di.ExploreSubcomponent
import com.nazartaraniuk.shopapplication.presentation.di.MainApplication
import javax.inject.Inject

class ExploreFragment : Fragment() {

    @Inject
    lateinit var viewModel: ExploreFragmentViewModel
    private lateinit var exploreSubcomponent: ExploreSubcomponent
    private var binding: FragmentExploreBinding? = null
    private val adapterManager = AdapterDelegatesManager(
        CategoriesListSmallAdapterDelegate(),
        TrendingItemAdapterDelegate(),
        ProductsListGridAdapterDelegate()
    )
    private val rootAdapter by lazy { DelegationAdapter(adapterManager) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupComponent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = object : LinearLayoutManager(requireActivity()) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        binding?.rvRootList?.let { recyclerView ->
            setAdapter(
                recyclerView,
                rootAdapter,
                linearLayoutManager
            )
        }
        subscribeToLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun subscribeToLiveData() = with(viewModel) {
        errorAction.observe(viewLifecycleOwner) {
            when (it) {
                is Events.Error -> {
                    rootAdapter.setItems(emptyList())
                    createErrorSnackBar(
                        requireView(),
                        layoutInflater,
                        it.message
                    )
//                    binding.pbLoading.visibility = it.visibility
                }
                else -> {}
            }
        }

        loadingState.observe(viewLifecycleOwner) {
            rootAdapter.setItems(it.items)
            binding?.pbLoading?.visibility = it.visibility
        }
    }

    private fun setupComponent() {
        exploreSubcomponent =
            (requireActivity().application as MainApplication)
                .appComponent
                .exploreSubcomponent()
                .build()
        exploreSubcomponent.inject(this)
    }
}