package com.example.stackexchange.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.stackexchange.R
import com.example.stackexchange.base.BaseFragment
import com.example.stackexchange.databinding.FragmentUserBinding
import javax.inject.Inject

class UserFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentUserBinding

    private val vm: UserViewModel by viewModels {
        vmFactory
    }

    private val args: UserFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_user,
                container,
                false
        )
        vm.setUserId(args.userId)
        vm.setToken(getAuthToken() ?: "")

        binding.apply {
            viewModel = vm
            lifecycleOwner = viewLifecycleOwner
        }


        return binding.root
    }

}