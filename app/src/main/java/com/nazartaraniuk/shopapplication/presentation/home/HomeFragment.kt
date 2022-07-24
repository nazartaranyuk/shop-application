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
import com.nazartaraniuk.shopapplication.presentation.common.Events
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
            CategoryListAdapterDelegate(requireActivity()),
            TrendingListAdapterDelegate(requireActivity()),
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()
        setAdapter(binding.rvRootList, rootAdapter)
    }

    private fun <R : RecyclerView.ViewHolder?> setAdapter(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<R>
    ) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun subscribeToLiveData() = with(viewModel) {
        errorAction.observe(viewLifecycleOwner) {
            when (it) {
                is Events.Error -> {
                    rootAdapter.setItems(emptyList())
                    HomeFragmentDialogError("Error! ${it.message}").show(parentFragmentManager, "DIALOG")
                }
            }
        }
        loadingState.observe(viewLifecycleOwner) {
            rootAdapter.setItems(it.items)
            binding.pbLoading.visibility = if (it.isLoading) View.VISIBLE else View.GONE
        }
    }
}