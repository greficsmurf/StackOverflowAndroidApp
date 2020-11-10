package com.example.stackexchange.ui.tagsearch

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackexchange.MainActivity
import com.example.stackexchange.R
import com.example.stackexchange.adapters.recycler.QuestionsPagedAdapter
import com.example.stackexchange.base.BaseFragment
import com.example.stackexchange.databinding.FragmentTagSearchBinding
import com.example.stackexchange.interfaces.QuestionsAdapterNavCallback
import com.example.stackexchange.utils.getResourceByLoadStates
import com.example.stackexchange.utils.navigateSafe
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TagSearchFragment : BaseFragment(){

    @Inject
    lateinit var vmFactory : ViewModelProvider.Factory

    private val args: TagSearchFragmentArgs by navArgs()

    private val vm: TagSearchViewModel by viewModels {
        vmFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentTagSearchBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_tag_search,
                container,
                false
        )

        vm.setTags(args.tags?.toList())
        args.tags?.firstOrNull()?.let {
            (activity as MainActivity).setToolbarTitle(getString(R.string.dynamic_tag_search_fragment_title, it))
        }
        val questionsAdapter = QuestionsPagedAdapter(object : QuestionsAdapterNavCallback{
            override fun navigate(navController: NavController, url: String, title: String) {
                navController.navigateSafe(TagSearchFragmentDirections.actionTagSearchFragmentToQuestionFragment(url, title))
            }

            override fun navigateToUser(navController: NavController, id: Long) {
                navController.navigateSafe(TagSearchFragmentDirections.actionTagSearchFragmentToUserFragment(id))
            }

            override fun navigateToTagSearch(navController: NavController, tags: List<String>) {
                navController.navigateSafe(TagSearchFragmentDirections.actionTagSearchFragmentSelf(tags.toTypedArray()))
            }
        }, requireContext())

        binding.apply {
            tagQuestionsRecycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = questionsAdapter
            }
            lifecycleOwner = viewLifecycleOwner
        }

        vm.tagSearchQuestions.observe(viewLifecycleOwner, Observer {
            viewLifecycleOwner.lifecycleScope.launch {
                questionsAdapter.submitData(it)
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            questionsAdapter.loadStateFlow.collect{
                binding.resource = getResourceByLoadStates(it)
            }
        }

        return binding.root
    }
}