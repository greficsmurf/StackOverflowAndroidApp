package com.example.stackexchange.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.stackexchange.R
import com.example.stackexchange.base.BaseFragment
import com.example.stackexchange.databinding.FragmentQuestionBinding
import javax.inject.Inject

class QuestionFragment : BaseFragment(){

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val vm: QuestionViewModel by viewModels {
        vmFactory
    }

    private val args: QuestionFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentQuestionBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_question,
                container,
                false
        )

        binding.questionWebView.loadUrl(args.url)

        return binding.root
    }


}