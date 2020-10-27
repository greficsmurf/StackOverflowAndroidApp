package com.example.stackexchange.base

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.stackexchange.R
import com.example.stackexchange.di.Injectable
import timber.log.Timber

abstract class BaseFragment : Fragment(), Injectable{

    protected fun toggleKeyBoard() = (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .toggleSoftInput(0, 0)

    fun getAuthToken(): String?{
        AccountManager.get(context).let { am ->
            var acc = am.getAccountsByType(getString(R.string.stackoverflow_account_type)).firstOrNull()
            if(acc == null)
            {
                am.addAccountExplicitly(Account(getString(R.string.stackoverflow_account_name), getString(R.string.stackoverflow_account_type)),null,null)
                acc = am.getAccountsByType(getString(R.string.stackoverflow_account_type)).firstOrNull()
            }
            val cachedToken = am.peekAuthToken(
                    acc, getString(R.string.stackoverflow_auth_token_type)
            )
            if(cachedToken == null){
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(requireContext().getString(R.string.auth_url))
                }
                startActivity(intent)
            }

            return cachedToken
        }
    }

}