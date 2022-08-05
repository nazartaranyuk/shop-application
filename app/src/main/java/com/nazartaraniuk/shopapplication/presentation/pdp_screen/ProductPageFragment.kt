package com.nazartaraniuk.shopapplication.presentation.pdp_screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.fragment.navArgs
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.FragmentProductPageBinding
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.createErrorSnackBar
import com.nazartaraniuk.shopapplication.presentation.common.setUpInterface
import com.nazartaraniuk.shopapplication.presentation.di.getComponent
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ProductPageFragment : Fragment() {

    private lateinit var binding: FragmentProductPageBinding
    private val args: ProductPageFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: ProductPageViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().application.getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProductPageInformation(args.id)
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(viewModel) {
        errorAction.observe(viewLifecycleOwner) {
            when (it) {
                is Events.Error -> {
                    createErrorSnackBar(
                        requireView(),
                        layoutInflater,
                        it.message
                    )
                }
            }
        }
        loadingState.observe(viewLifecycleOwner) { state ->
            setUpInterface(state.item, binding)
            binding.ivAddToFavourite.setOnClickListener {
                it.background = R.drawable.ic_favorites_checked.toDrawable()
                viewModel.saveFavorite(state.item)
            }
            binding.pbLoading.visibility = state.visibility
        }
    }

}