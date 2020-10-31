package com.example.stackexchange.ui.user

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackexchange.R
import com.example.stackexchange.adapters.recycler.QuestionsPagedAdapter
import com.example.stackexchange.base.BaseFragment
import com.example.stackexchange.databinding.FragmentUserBinding
import com.example.stackexchange.domain.models.User
import com.example.stackexchange.interfaces.QuestionsAdapterNavCallback
import kotlinx.coroutines.launch
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

        val questionsAdapter = QuestionsPagedAdapter(object : QuestionsAdapterNavCallback{
            override fun navigate(navController: NavController, url: String, title: String) {
                navController.navigate(UserFragmentDirections.actionUserFragmentToQuestionFragment(url, title))
            }

            override fun navigateToUser(navController: NavController, id: Long) {
                navController.navigate(UserFragmentDirections.actionUserFragmentSelf(id))
            }
        })

        vm.setUserId(args.userId)

        binding.apply {
            userQuestions.apply {
                adapter = questionsAdapter
                layoutManager = LinearLayoutManager(context)
            }

            viewModel = vm
            lifecycleOwner = viewLifecycleOwner
        }
        vm.userQuestionsDataSource.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewLifecycleOwner.lifecycleScope.launch {
                    questionsAdapter.submitData(it)
                }
            }
        })
        return binding.root
    }


}