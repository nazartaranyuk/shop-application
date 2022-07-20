package com.nazartaraniuk.shopapplication.presentation.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazartaraniuk.shopapplication.databinding.FragmentHomeBinding
import com.nazartaraniuk.shopapplication.presentation.Events
import com.nazartaraniuk.shopapplication.presentation.adapters.AdapterDelegatesManager
import com.nazartaraniuk.shopapplication.presentation.adapters.CategoryItemAdapterDelegate
import com.nazartaraniuk.shopapplication.presentation.adapters.DelegationAdapter
import com.nazartaraniuk.shopapplication.presentation.adapters.TrendingItemAdapterDelegate
import com.nazartaraniuk.shopapplication.presentation.di.getComponent
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import javax.inject.Inject


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val categoriesManager by lazy {
        AdapterDelegatesManager(CategoryItemAdapterDelegate())
    }
    private val trendingManager by lazy {
        AdapterDelegatesManager(TrendingItemAdapterDelegate())
    }

    private val trendingListAdapter by lazy { DelegationAdapter(trendingManager) }
    private val categoriesAdapter by lazy { DelegationAdapter(categoriesManager) }

    @Inject
    lateinit var viewModel: HomeFragmentViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().application.getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setAdapter(binding.rvCategories, categoriesAdapter)
        setAdapter(binding.rvTrendingNow, trendingListAdapter)

        subscribeToLiveData()
        return binding.root
    }

    private fun <R : RecyclerView.ViewHolder?> setAdapter(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<R>
    ) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun subscribeToLiveData() = with(viewModel) {
        updateCategoriesList.observe(viewLifecycleOwner) {
            when (it) {
                is Events.Success<*> -> categoriesAdapter.setItems(it.data as List<String>)
                is Events.Error -> {
                    categoriesAdapter.setItems(emptyList())
                }
                else -> {}
            }
        }
        updateTrendingList.observe(viewLifecycleOwner) {
            when(it) {
                is Events.Success<*> -> trendingListAdapter.setItems(it.data as List<ProductItemModel>)
                is Events.Error -> trendingListAdapter.setItems(emptyList())
                else -> {}
            }
        }
    }
}