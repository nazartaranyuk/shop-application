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
import com.nazartaraniuk.shopapplication.presentation.di.getComponent
import javax.inject.Inject

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private val adapterManager = AdapterDelegatesManager(
        CategoriesListSmallAdapterDelegate(),
        ProductItemAdapterDelegate(),
        ProductsListGridAdapterDelegate()
    )
    private val rootAdapter by lazy { DelegationAdapter(adapterManager) }

    @Inject
    lateinit var viewModel: ExploreFragmentViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().application.getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = object : LinearLayoutManager(requireActivity()) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        setAdapter(
            binding.rvRootList,
            rootAdapter,
            linearLayoutManager
        )
        subscribeToLiveData()
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
            }
        }

        loadingState.observe(viewLifecycleOwner) {
            rootAdapter.setItems(it.items)
            binding.pbLoading.visibility = it.visibility
        }
    }
}