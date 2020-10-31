package com.example.stackexchange.ui.authuser

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.stackexchange.R
import com.example.stackexchange.base.BaseApplication
import com.example.stackexchange.base.BaseFragment
import com.example.stackexchange.databinding.FragmentAuthUserBinding
import javax.inject.Inject

class AuthUserFragment : BaseFragment(){

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    @Inject
    lateinit var baseApp: BaseApplication

    private val vm: AuthUserViewModel by viewModels {
        vmFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
            setHasOptionsMenu(true)

        val binding: FragmentAuthUserBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_auth_user,
                container,
                false
        )

        binding.apply {
            viewModel = vm
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_user_menu, menu)


        menu.findItem(R.id.log_out_menu).setOnMenuItemClickListener {
            baseApp.stackOverflowAuthenticator.logOff()
            findNavController().popBackStack()
            true
        }
    }
}