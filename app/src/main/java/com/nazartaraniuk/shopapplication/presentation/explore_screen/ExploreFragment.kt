package com.nazartaraniuk.shopapplication.presentation.explore_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.CategorySmallItemBinding
import com.nazartaraniuk.shopapplication.databinding.FragmentExploreBinding
import com.nazartaraniuk.shopapplication.presentation.adapters.*
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.createErrorSnackBar
import com.nazartaraniuk.shopapplication.presentation.common.setAdapter
import com.nazartaraniuk.shopapplication.presentation.di.ExploreSubcomponent
import com.nazartaraniuk.shopapplication.presentation.di.MainApplication
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.nazartaraniuk.shopapplication.presentation.pdp_screen.ProductPageFragmentArgs
import javax.inject.Inject

class ExploreFragment : Fragment() {

    @Inject
    lateinit var viewModel: ExploreFragmentViewModel

    private lateinit var exploreSubcomponent: ExploreSubcomponent

    private var binding: FragmentExploreBinding? = null

    private val clickListener: (String) -> Unit = { category ->
        viewModel.onCategoryClicked(category)
    }

    private val adapterManager = AdapterDelegatesManager(
        CategoriesListSmallAdapterDelegate(clickListener),
    )

    private val manager = AdapterDelegatesManager(
        ProductItemAdapterDelegate()
    )

    private val categoriesAdapter by lazy { DelegationAdapter(adapterManager) }
    private val gridAdapter by lazy { DelegationAdapter(manager) }

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
        val clickedCategory = arguments?.getString("category")
        viewModel.onCategoryClicked(clickedCategory ?: "All")

        binding?.recyclerView?.let { recyclerView ->
            setAdapter(
                recyclerView,
                gridAdapter,
                GridLayoutManager(
                    requireActivity(),
                    2
                )
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
                    categoriesAdapter.setItems(emptyList())
                    createErrorSnackBar(
                        requireView(),
                        layoutInflater,
                        it.message
                    )
                    binding?.pbLoading?.visibility = it.visibility
                }
                else -> {}
            }
        }

        loadingState.observe(viewLifecycleOwner) { state ->

            binding?.linearLayout?.removeAllViews()
            state.categories.filterIsInstance<CategoryItemModel>().forEach { category ->
                val viewBinding = CategorySmallItemBinding.inflate(
                    LayoutInflater.from(binding?.root?.context),
                    binding?.root,
                    false
                ).apply {
                    tvCategorySmallDescription.text = category.category
                }
                if (category.isSelected) {
                    viewBinding.root.setBackgroundResource(
                        R.drawable.small_category_frame_selected
                    )
                }
                viewBinding.root.setOnClickListener {
                    clickListener(category.category)
                }
                binding?.linearLayout?.addView(viewBinding.root)
            }


            gridAdapter.setItems(state.products)
            binding?.recyclerView?.scrollToPosition(0)
            binding?.pbLoading?.isVisible = state.isProgressBarVisible
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