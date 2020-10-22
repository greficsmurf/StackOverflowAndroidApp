package com.example.stackexchange.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.stackexchange.R
import com.example.stackexchange.adapters.recycler.QuestionsAdapter
import com.example.stackexchange.databinding.FragmentHomeBinding
import com.example.stackexchange.di.Injectable
import com.example.stackexchange.di.ViewModelFactory
import com.example.stackexchange.interfaces.QuestionsAdapterNavCallback
import com.example.stackexchange.repo.QuestionSort
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val vm: HomeViewModel by viewModels {
        vmFactory
    }
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

    class HomeQuestionsPagerAdapter(f: Fragment) : FragmentStateAdapter(f){
        override fun getItemCount() = QuestionSort.sortList.size

        override fun createFragment(position: Int) = HomeQuestionsTab.getInstance(position)
    }
}