package com.example.stackexchange.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackexchange.R
import com.example.stackexchange.adapters.recycler.QuestionsPagedAdapter
import com.example.stackexchange.base.BaseFragment
import com.example.stackexchange.databinding.TabQuestionsBinding
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.User
import com.example.stackexchange.interfaces.QuestionsAdapterNavCallback
import com.example.stackexchange.repo.QuestionSort
import com.example.stackexchange.ui.tagsearch.TagSearchFragmentDirections
import com.example.stackexchange.utils.getResourceByLoadStates
import com.example.stackexchange.utils.navigateSafe
import kotlinx.android.synthetic.main.tab_questions.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
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
        val questionsAdapter = QuestionsPagedAdapter(
                object : QuestionsAdapterNavCallback {
                    override fun navigate(navController: NavController, url: String, title: String) {
                        navController.navigateSafe(HomeFragmentDirections.actionHomeFragmentToQuestionFragment(url, title))
                    }

                    override fun navigateToUser(navController: NavController, id: Long) {
                        navController.navigateSafe(HomeFragmentDirections.actionHomeFragmentToUserFragment2(id))
                    }

                    override fun navigateToTagSearch(navController: NavController, tags: List<String>) {
                        navController.navigateSafe(HomeFragmentDirections.actionHomeFragmentToTagSearchFragment(tags.toTypedArray()))
                    }
                },
                requireContext()
        )
        binding.apply {
            questionsRecycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = questionsAdapter
            }
            refresher.setOnRefreshListener {
                vm.refresh()

            }
            lifecycleOwner = viewLifecycleOwner
        }
        vm.questionsDataSource.observe(viewLifecycleOwner, Observer { pageData ->
            viewLifecycleOwner.lifecycleScope.launch {
                questionsAdapter.submitData(pageData.map { it.toDomainModel(User(userId = it.ownerId)) })
                refresher.isRefreshing = false
            }
        })
        viewLifecycleOwner.lifecycleScope.launch {
            questionsAdapter.loadStateFlow.collect {
                binding.resource = getResourceByLoadStates(it)
            }
        }


        return binding.root
    }
}