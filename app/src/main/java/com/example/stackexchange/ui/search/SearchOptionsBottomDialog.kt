package com.example.stackexchange.ui.search

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stackexchange.R
import com.example.stackexchange.adapters.recycler.TagsAdapter
import com.example.stackexchange.databinding.DialogSearchOptionsBinding
import com.example.stackexchange.di.Injectable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class SearchOptionsBottomDialog : BottomSheetDialogFragment(), Injectable{

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val vm: SearchViewModel by activityViewModels {
        vmFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: DialogSearchOptionsBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_search_options,
                container, false
        )

        val tagAdapter = TagsAdapter(requireContext(), vm.selectedTagList)

        binding.apply {
            recyclerTags.apply {
                layoutManager = GridLayoutManager(context, 4)
                adapter = tagAdapter
            }
            searchBtn.setOnClickListener {
                vm.refresh()
                dismiss()
            }
            lifecycleOwner = viewLifecycleOwner
        }

        vm.tags.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                tagAdapter.submitList(it)
            }
        })

        return binding.root
    }



}