package com.example.stackexchange.ui.question

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.stackexchange.R
import com.example.stackexchange.base.BaseFragment
import com.example.stackexchange.databinding.FragmentQuestionBinding
import timber.log.Timber
import java.net.CookieManager
import javax.inject.Inject

class QuestionFragment : BaseFragment(){

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val vm: QuestionViewModel by viewModels {
        vmFactory
    }

    private val args: QuestionFragmentArgs by navArgs()

    private val mWebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Timber.d("page finished")
            vm.setPageLoaded(true)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            vm.setPageLoaded(false)
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            vm.setError(error.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentQuestionBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_question,
                container,
                false
        )

        binding.apply {
            viewModel = vm
            lifecycleOwner = viewLifecycleOwner
            questionWebView.webViewClient = mWebViewClient
            questionWebView.loadUrl(args.url)
            pageRefresher.setOnRefreshListener {
                questionWebView.reload()
                pageRefresher.isRefreshing = false
            }
        }

        return binding.root
    }


}