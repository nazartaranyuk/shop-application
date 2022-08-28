package com.nazartaraniuk.shopapplication.presentation.search_screen

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.FragmentSearchBinding
import com.nazartaraniuk.shopapplication.presentation.dialogs.DialogError
import com.nazartaraniuk.shopapplication.presentation.adapters.AdapterDelegatesManager
import com.nazartaraniuk.shopapplication.presentation.adapters.DelegationAdapter
import com.nazartaraniuk.shopapplication.presentation.adapters.TrendingItemAdapterDelegate
import com.nazartaraniuk.shopapplication.presentation.common.setAdapter
import com.nazartaraniuk.shopapplication.presentation.di.MainApplication
import com.nazartaraniuk.shopapplication.presentation.di.SearchSubcomponent
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import javax.inject.Inject

class SearchFragment : Fragment(), MainContract.View {

    private val adapterManager by lazy {
        AdapterDelegatesManager(
            TrendingItemAdapterDelegate()
        )
    }
    private val searchAdapter by lazy {
        DelegationAdapter(adapterManager)
    }
    private var binding: FragmentSearchBinding? = null
    override fun onStart() {
        super.onStart()
        presenter.getAllData()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    @Inject
    lateinit var presenter: SearchFragmentPresenter
    lateinit var searchSubcomponent: SearchSubcomponent
    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUpComponent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        presenter.attachView(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val isTablet = this.resources.getBoolean(R.bool.isTablet)
        val spanCount = if (isTablet) SPAN_COUNT_TABLET else SPAN_COUNT_MOBILE
        binding?.rvSearchList?.let { recyclerView ->
            setAdapter(
                recyclerView,
                searchAdapter,
                GridLayoutManager(requireActivity(), spanCount)
            )
        }
    }

    override fun displayData(list: List<ProductItemModel>) {
        binding?.etSearchField?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun afterTextChanged(editText: Editable?) {
                searchAdapter.setItems(presenter.filterList(list, editText.toString()))
                searchAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun setUpComponent() {
        searchSubcomponent =
            (requireActivity().application as MainApplication)
                .appComponent
                .searchSubcomponent()
                .build()
        searchSubcomponent.inject(this)
    }

    override fun displayError(message: String) {
        DialogError(message) // TODO replace it later
    }

    companion object {
        const val SPAN_COUNT_MOBILE = 2
        const val SPAN_COUNT_TABLET = 4
    }
}