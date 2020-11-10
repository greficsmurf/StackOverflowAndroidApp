package com.example.stackexchange.ui.home

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.stackexchange.R
import com.example.stackexchange.databinding.FragmentHomeBinding
import com.example.stackexchange.di.Injectable
import com.example.stackexchange.di.ViewModelFactory
import com.example.stackexchange.domain.models.User
import com.example.stackexchange.interfaces.QuestionsAdapterNavCallback
import com.example.stackexchange.repo.QuestionSort
import com.example.stackexchange.utils.navigateSafe
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val vm: HomeViewModel by viewModels {
        vmFactory
    }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home,
                container,
                false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewPager.adapter = HomeQuestionsPagerAdapter(this@HomeFragment)
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = QuestionSort.sortList.getOrNull(position)?.title
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_home_menu, menu)
        menu.findItem(R.id.settings_menu).setOnMenuItemClickListener {
            findNavController().navigateSafe(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
            true
        }

        menu.findItem(R.id.account_menu).setOnMenuItemClickListener {
            findNavController().navigateSafe(HomeFragmentDirections.actionHomeFragmentToAuthUserFragment())
            true
        }
    }

    class HomeQuestionsPagerAdapter(f: Fragment) : FragmentStateAdapter(f){
        override fun getItemCount() = QuestionSort.sortList.size

        override fun createFragment(position: Int) = HomeQuestionsTab.getInstance(position)
    }
}