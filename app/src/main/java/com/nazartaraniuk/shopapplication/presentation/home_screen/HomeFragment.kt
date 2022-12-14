package com.nazartaraniuk.shopapplication.presentation.home_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.FragmentHomeBinding
import com.nazartaraniuk.shopapplication.presentation.adapters.*
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.buttonAnimation
import com.nazartaraniuk.shopapplication.presentation.common.createErrorSnackBar
import com.nazartaraniuk.shopapplication.presentation.common.setAdapter
import com.nazartaraniuk.shopapplication.presentation.di.HomeSubcomponent
import com.nazartaraniuk.shopapplication.presentation.di.MainApplication
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import com.nazartaraniuk.shopapplication.presentation.pdp_screen.ProductPageFragmentArgs
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModel: HomeFragmentViewModel
    private lateinit var homeSubcomponent: HomeSubcomponent
    private var binding: FragmentHomeBinding? = null
    private val adapterManager by lazy {
        AdapterDelegatesManager(
            ImageItemAdapterDelegate(),
            TitleItemAdapterDelegate(navigateToPage),
            CategoryListAdapterDelegate(openPage),
            TrendingListAdapterDelegate(openProductPage),
            TrendingItemAdapterDelegate(openProductPage)
        )
    }
    private val rootAdapter by lazy { DelegationAdapter(adapterManager) }

    private val openProductPage: (View, ProductItemModel) -> Unit = { view, model ->
        val navController = Navigation.findNavController(view)
        val bundle = bundleOf(ID to model.id)
        buttonAnimation(view, requireActivity())
        navController.navigate(R.id.action_global_productPageFragment, bundle)
    }

    private val navigateToPage: (Int) -> Unit = { link ->
        findNavController().navigate(link)
    }

    private val openPage: (String) -> Unit = { string ->
        val bundle = bundleOf("category" to string)
        findNavController().navigate(R.id.action_homeFragment_to_exploreFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpComponent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
        setUpListeners()

        binding?.rvRootList?.let { recyclerView ->
            setAdapter(
                recyclerView, rootAdapter, LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            )
        }
    }

    private fun setUpComponent() {
        homeSubcomponent =
            (requireActivity().application as MainApplication)
                .appComponent
                .homeSubcomponent()
                .build()
        homeSubcomponent.inject(this)
    }

    private fun setUpListeners() {
        binding?.etSearchField?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
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
                    binding?.shimmerAnimationLoading?.isVisible = it.visibility
                    binding?.rvRootList?.isVisible = it.visibility
                }
                else -> {}
            }
        }
        loadingState.observe(viewLifecycleOwner) {
            rootAdapter.setItems(it.items)
            binding?.shimmerAnimationLoading?.isVisible = it.animationVisibility
            binding?.rvRootList?.isVisible = it.interfaceVisibility
        }
    }
    companion object {
        const val ID = "id"
    }
}