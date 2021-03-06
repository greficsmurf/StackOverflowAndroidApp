package com.example.stackexchange.base

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.stackexchange.R
import com.example.stackexchange.di.Injectable
import timber.log.Timber

abstract class BaseFragment : Fragment(), Injectable{

    protected fun toggleKeyBoard() = (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .toggleSoftInput(0, 0)
}