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
import com.nazartaraniuk.shopapplication.databinding.FragmentSearchBinding
import com.nazartaraniuk.shopapplication.presentation.dialogs.DialogError
import com.nazartaraniuk.shopapplication.presentation.adapters.AdapterDelegatesManager
import com.nazartaraniuk.shopapplication.presentation.adapters.DelegationAdapter
import com.nazartaraniuk.shopapplication.presentation.adapters.ProductItemAdapterDelegate
import com.nazartaraniuk.shopapplication.presentation.common.setAdapter
import com.nazartaraniuk.shopapplication.presentation.di.getComponent
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import javax.inject.Inject

class SearchFragment : Fragment(), MainContract.View {

    private val adapterManager by lazy {
        AdapterDelegatesManager(
            ProductItemAdapterDelegate()
        )
    }
    private val searchAdapter by lazy {
        DelegationAdapter(adapterManager)
    }
    private lateinit var binding: FragmentSearchBinding
    override fun onStart() {
        super.onStart()
        presenter.getAllData()
    }

    @Inject
    lateinit var presenter: SearchFragmentPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().application.getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        presenter.attachView(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setAdapter(
            binding.rvSearchList,
            searchAdapter,
            GridLayoutManager(requireActivity(), 2)
        )
    }

    override fun displayData(list: List<ProductItemModel>) {
        binding.etSearchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editText: Editable?) {
                searchAdapter.setItems(presenter.filterList(list, editText.toString()))
                searchAdapter.notifyDataSetChanged()
            }
        })
    }



    override fun displayError(message: String) {
        DialogError(message)
    }


}