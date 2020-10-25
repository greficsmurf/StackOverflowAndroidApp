package com.example.stackexchange.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackexchange.R
import com.example.stackexchange.adapters.recycler.QuestionsPagedAdapter
import com.example.stackexchange.base.BaseFragment
import com.example.stackexchange.databinding.FragmentSearchBinding
import com.example.stackexchange.interfaces.QuestionsAdapterNavCallback
import com.example.stackexchange.utils.getResourceByLoadStates
import com.example.stackexchange.vo.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timerTask

class SearchFragment : BaseFragment(){

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val vm: SearchViewModel by viewModels {
        vmFactory
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_search,
                container,
                false
        )
        val questionsAdapter = QuestionsPagedAdapter(object : QuestionsAdapterNavCallback{
            override fun navigate(navController: NavController, url: String, title: String) {
                navController.navigate(SearchFragmentDirections.actionSearchFragmentToQuestionFragment(url, title))
            }
        })

        binding.apply {
            recyclerQuestions.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = questionsAdapter
            }
            lifecycleOwner = viewLifecycleOwner
        }

        vm.questionsDataSource.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewLifecycleOwner.lifecycleScope.launch {
                questionsAdapter.submitData(it)
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            questionsAdapter.loadStateFlow.collect {
                binding.resource = getResourceByLoadStates(it)
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_fragment_menu, menu)

        try {
            val menuItem = menu.findItem(R.id.searchBar)
            val searchView = menuItem.actionView as EditText

            searchView.apply {
                addTextChangedListener(
                        object : TextWatcher {
                            private var timer = Timer()
                            private val DELAY = 700L

                            override fun afterTextChanged(s: Editable?) {
                            }

                            override fun beforeTextChanged(
                                    s: CharSequence?,
                                    start: Int,
                                    count: Int,
                                    after: Int
                            ) {
                            }

                            override fun onTextChanged(
                                    s: CharSequence?,
                                    start: Int,
                                    before: Int,
                                    count: Int
                            ) {
                                timer.cancel()

                                if(s.isNullOrBlank())
                                {
                                    vm.setSearchText(s.toString())
                                }

                                timer = Timer()
                                timer.schedule(
                                        timerTask {
                                            vm.setSearchText(s.toString())
                                        },
                                        DELAY
                                )
                            }

                        }
                )
                width = Int.MAX_VALUE
                setText(vm.searchString.value)
                selectAll()

                requestFocus()
                toggleKeyBoard()
            }
        }catch (e: Exception){
            Snackbar.make(requireView(), getString(R.string.search_bar_init_error), Snackbar.LENGTH_LONG).show()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

}