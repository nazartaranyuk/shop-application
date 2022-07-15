package com.nazartaraniuk.shopapplication.presentation.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nazartaraniuk.shopapplication.databinding.FragmentHomeBinding
import com.nazartaraniuk.shopapplication.presentation.Events
import com.nazartaraniuk.shopapplication.presentation.di.getComponent
import javax.inject.Inject


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter by lazy { CategoriesAdapter() }

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
        setAdapter()
        subscribeToLiveData()
        return binding.root
    }

    private fun setAdapter() {
        binding.rvCategories.adapter = adapter
        binding.rvCategories.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun subscribeToLiveData() {
        viewModel.updateList.observe(viewLifecycleOwner) {
            when(it) {
                is Events.Success<*> -> adapter.updateList(it.data as List<String>)
                else -> {}
            }
        }
    }
}