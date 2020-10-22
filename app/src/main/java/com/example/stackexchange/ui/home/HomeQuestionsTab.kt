package com.example.stackexchange.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackexchange.R
import com.example.stackexchange.adapters.recycler.QuestionsAdapter
import com.example.stackexchange.base.BaseFragment
import com.example.stackexchange.databinding.TabQuestionsBinding
import com.example.stackexchange.interfaces.QuestionsAdapterNavCallback
import com.example.stackexchange.repo.QuestionSort
import kotlinx.android.synthetic.main.tab_questions.*
import javax.inject.Inject

class HomeQuestionsTab : BaseFragment() {

    companion object{
        const val SORT_ARG_KEY = "sort_pos"
        fun getInstance(sortPos: Int): HomeQuestionsTab{
            val fragment = HomeQuestionsTab()
            fragment.arguments = Bundle().apply {
                putInt(SORT_ARG_KEY, sortPos)
            }

            return fragment
        }
    }

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: TabQuestionsBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.tab_questions,
                container,
                false
        )
        val sortPos = arguments?.getInt(SORT_ARG_KEY) ?: 0

        val vm = ViewModelProvider(this, vmFactory).get(sortPos.toString(), HomeViewModel::class.java)
        vm.setQuestionSort(QuestionSort.sortList.getOrNull(sortPos))
        val questionsAdapter = QuestionsAdapter(
                object : QuestionsAdapterNavCallback {
                    override fun navigate(navController: NavController, url: String) {
                        navController.navigate(HomeFragmentDirections.actionHomeFragmentToQuestionFragment(url))
                    }
                }
        )

        binding.apply {
            questionsRecycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = questionsAdapter
            }
            refresher.setOnRefreshListener {
                vm.refresh()

            }
            viewModel = vm
            lifecycleOwner = viewLifecycleOwner
        }

        vm.questions.observe(viewLifecycleOwner, Observer { list ->
            if (!list.isNullOrEmpty())
            {
                questionsAdapter.submitList(list)
                refresher.isRefreshing = false
            }
        })

        return binding.root
    }
}