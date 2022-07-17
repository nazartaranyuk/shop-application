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
import com.nazartaraniuk.shopapplication.presentation.di.getComponent
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import javax.inject.Inject


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val categoriesAdapter by lazy { CategoriesAdapter() }
    private val trendingListAdapter by lazy { TrendingListAdapter() }

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
                is Events.Success<*> -> categoriesAdapter.updateList(it.data as List<String>)
                is Events.Error -> {
                    categoriesAdapter.updateList(emptyList())
                }
                else -> {}
            }
        }
        updateTrendingList.observe(viewLifecycleOwner) {
            when(it) {
                is Events.Success<*> -> trendingListAdapter.updateList(it.data as List<ProductItemModel>)
                is Events.Error -> trendingListAdapter.updateList(emptyList())
                else -> {}
            }
        }
    }
}