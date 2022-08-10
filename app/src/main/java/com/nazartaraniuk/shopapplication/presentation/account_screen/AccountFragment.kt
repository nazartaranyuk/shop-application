package com.nazartaraniuk.shopapplication.presentation.account_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nazartaraniuk.shopapplication.MainActivity
import com.nazartaraniuk.shopapplication.R
import com.nazartaraniuk.shopapplication.databinding.FragmentAccountBinding
import com.nazartaraniuk.shopapplication.presentation.common.loadImage
import com.squareup.picasso.Picasso

class AccountFragment : Fragment() {

    private var binding: FragmentAccountBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        setInterface()
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // TODO This function for testing purpose
    private fun setInterface() {
        val user = (requireActivity() as MainActivity).auth.currentUser
        loadImage(
            binding?.ivAccountAvatar ?: error("Can't load photo"),
            user?.photoUrl.toString()
        )
        binding?.tvAccountEmail?.text = user?.email
        binding?.tvAccountFirstName?.text = user?.displayName

    }
}