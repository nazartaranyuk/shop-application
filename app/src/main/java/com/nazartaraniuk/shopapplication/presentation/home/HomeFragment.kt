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
import com.nazartaraniuk.shopapplication.presentation.adapters.*
import com.nazartaraniuk.shopapplication.presentation.di.getComponent
import javax.inject.Inject


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val adapterManager by lazy {
        AdapterDelegatesManager(
            ImageItemAdapterDelegate(),
            TitleItemAdapterDelegate(),
            CategoryItemAdapterDelegate(),
            TrendingItemAdapterDelegate()
        )
    }

    private val rootAdapter by lazy { DelegationAdapter(adapterManager) }

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

        setAdapter(binding.rvRootList, rootAdapter)

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
        updateList.observe(viewLifecycleOwner) {
            when (it) {
                is Events.Success<*> -> {
                    rootAdapter.setItems(it.data as List<DisplayableItem>)
                }
                is Events.Error -> {
                    rootAdapter.setItems(emptyList())
                }
                else -> {}
            }
        }
    }
}