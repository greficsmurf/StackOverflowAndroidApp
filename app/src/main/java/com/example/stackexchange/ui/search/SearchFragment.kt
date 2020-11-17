package com.example.stackexchange.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackexchange.R
import com.example.stackexchange.adapters.recycler.QuestionsPagedAdapter
import com.example.stackexchange.base.BaseFragment
import com.example.stackexchange.customviews.PreferencesEditText
import com.example.stackexchange.databinding.FragmentSearchBinding
import com.example.stackexchange.domain.models.Tag
import com.example.stackexchange.interfaces.DefaultCallback
import com.example.stackexchange.interfaces.QuestionsAdapterNavCallback
import com.example.stackexchange.utils.getResourceByLoadStates
import com.example.stackexchange.utils.navigateSafe
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

    private val vm: SearchViewModel by activityViewModels {
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
                navController.navigateSafe(SearchFragmentDirections.actionSearchFragmentToQuestionFragment(url, title))
            }
            override fun navigateToUser(navController: NavController, id: Long) {
                navController.navigateSafe(SearchFragmentDirections.actionSearchFragmentToUserFragment(id))
            }
            override fun navigateToTagSearch(navController: NavController, tags: List<String>) {
                navController.navigateSafe(SearchFragmentDirections.actionSearchFragmentToTagSearchFragment(tags.toTypedArray()))
            }
        }, requireContext())

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

        /**
         *  (Workaround for keeping tagsResource coroutine alive in options menu)
         *
         *  SearchOptionsBottomDialog observes tagsResource via its viewLifecycleOwner, so coroutine gets cancelled,
         *  and connecting to database is required again, this line keeps coroutine alive, so tagsResource can be accessed
         *  without delay
         *
         */
        vm.tagsResource.observe(viewLifecycleOwner, object : Observer<Resource<List<Tag>>>{
            override fun onChanged(t: Resource<List<Tag>>?) {
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
            val searchView = menuItem.actionView as PreferencesEditText

            searchView.apply {
                setOnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        vm.setSearchText(text.toString())
                        true
                    }
                    else
                        false
                }
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

                addPreferenceClickCallback(
                        object : DefaultCallback{
                            override fun run() {
                                this@SearchFragment.findNavController().navigateSafe(SearchFragmentDirections.actionSearchFragmentToSearchOptionsBottomDialog())
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