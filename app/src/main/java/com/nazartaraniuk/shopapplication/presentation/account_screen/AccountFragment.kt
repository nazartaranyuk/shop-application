package com.nazartaraniuk.shopapplication.presentation.account_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import com.nazartaraniuk.shopapplication.presentation.MainActivity
import com.nazartaraniuk.shopapplication.databinding.FragmentAccountBinding
import com.nazartaraniuk.shopapplication.presentation.signin_screen.SignInActivity
import com.nazartaraniuk.shopapplication.presentation.notifications.NotificationHelper
import com.nazartaraniuk.shopapplication.presentation.common.loadImage
import com.nazartaraniuk.shopapplication.presentation.di.AccountSubcomponent
import com.nazartaraniuk.shopapplication.presentation.di.MainApplication
import javax.inject.Inject

class AccountFragment : Fragment() {

    private var binding: FragmentAccountBinding? = null

    @Inject
    lateinit var viewModel: AccountFragmentViewModel
    @Inject
    lateinit var notificationHelper: NotificationHelper
    private lateinit var accountSubcomponent: AccountSubcomponent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        accountSubcomponent =
            (requireActivity().application as MainApplication)
                .appComponent
                .accountSubcomponent()
                .build()
        accountSubcomponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
        setInterface()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun subscribeToLiveData() = with(viewModel) {
        isChecked.observe(viewLifecycleOwner) {
            binding?.switchSendNotifications?.isChecked = it
        }
        isOnCheckedAction.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), TOAST_TEXT, Toast.LENGTH_LONG).show()
            Log.d(TAG, LOG_MESSAGE)
        }
    }

    private fun setInterface() {
        val user = (requireActivity() as MainActivity).auth.currentUser
        loadImage(
            binding?.ivAccountAvatar ?: error("Can't load photo"),
            user?.photoUrl.toString()
        )
        binding?.switchSendNotifications?.setOnClickListener {
            val isChecked = (it as Switch).isChecked
            viewModel.shouldShowNotification(isChecked)
        }
        binding?.tvAccountEmail?.text = user?.email
        binding?.tvAccountFirstName?.text = user?.displayName
        binding?.tvLogOut?.setOnClickListener {
            val activity = (requireActivity() as MainActivity)
            activity.auth.signOut()
            startActivity(Intent(activity, SignInActivity::class.java))
            activity.finish()
        }
    }

    companion object {
        const val LOG_MESSAGE = "Saved to shared preferences"
        const val TAG = "Account Fragment"
        const val TOAST_TEXT = "Okay"
    }
}