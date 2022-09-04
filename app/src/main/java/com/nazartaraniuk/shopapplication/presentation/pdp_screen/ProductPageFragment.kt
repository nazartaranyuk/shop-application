package com.nazartaraniuk.shopapplication.presentation.pdp_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startForegroundService
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.FragmentProductPageBinding
import com.nazartaraniuk.shopapplication.presentation.common.Events
import com.nazartaraniuk.shopapplication.presentation.common.buttonAnimation
import com.nazartaraniuk.shopapplication.presentation.common.createErrorSnackBar
import com.nazartaraniuk.shopapplication.presentation.common.setUpInterface
import com.nazartaraniuk.shopapplication.presentation.di.MainApplication
import com.nazartaraniuk.shopapplication.presentation.di.ProductPageSubcomponent
import javax.inject.Inject

class ProductPageFragment : Fragment() {

    private var binding: FragmentProductPageBinding? = null
    private val args: ProductPageFragmentArgs by navArgs()
    private lateinit var productPageSubcomponent: ProductPageSubcomponent

    @Inject
    lateinit var viewModel: ProductPageViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpComponent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductPageBinding.inflate(layoutInflater)
        setUpComponent()
        return binding?.root
    }

    private fun createNotification() {
        val intent = Intent(requireActivity(), ProductPageService::class.java)
        intent.putExtra(PRODUCT_NAME_KEY, binding?.tvProductItemName?.text)
        intent.putExtra(PRODUCT_PRICE_KEY, binding?.tvProductItemPrice?.text)
        startForegroundService(requireActivity(), intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProductPageInformation(args.id)
        subscribeToLiveData()
    }

    private fun setUpComponent() {
        productPageSubcomponent =
            (requireActivity().application as MainApplication)
                .appComponent
                .productPageSubcomponent()
                .build()
        productPageSubcomponent.inject(this)
    }

    override fun onStop() {
        super.onStop()
        activity.let { activity ->
            activity?.applicationContext?.stopService(
                Intent(
                    activity,
                    ProductPageService::class.java
                )
            )
        }
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
                else -> {}
            }
        }
        loadingState.observe(viewLifecycleOwner) { state ->
            binding?.let { binding -> setUpInterface(state.item, binding) }
            val heartButton = binding?.ivAddToFavorites
            createNotification()
            binding?.btnAddToCart?.setOnClickListener {
                viewModel.buyProduct(state.item)
                Toast.makeText(requireActivity(), getString(R.string.toast_text), Toast.LENGTH_LONG)
                    .show()
            }
            heartButton?.isVisible = state.heartButtonVisibility
            heartButton?.setBackgroundResource(state.resourceImg.resource)
            heartButton?.setOnClickListener {
                if (state.isFavorite) {
                    buttonAnimation(it, requireActivity())
                    it.setBackgroundResource(R.drawable.ic_favorites_unchecked)
                    viewModel.deleteFromFavorites(state.item)
                } else {
                    buttonAnimation(it, requireActivity())
                    it.setBackgroundResource(R.drawable.ic_favorites_checked)
                    viewModel.saveFavorite(state.item)
                }
            }
            binding?.pbLoading?.isVisible = state.loadingVisibility
        }
    }

    companion object {
        const val PRODUCT_NAME_KEY = "product_name_key"
        const val PRODUCT_PRICE_KEY = "product_price_key"
    }
}