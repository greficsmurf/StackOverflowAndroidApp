package com.example.stackexchange.authenticator

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.example.stackexchange.R
import timber.log.Timber
import java.lang.UnsupportedOperationException

class Authenticator(val context: Context
): AbstractAccountAuthenticator(context){

    private val accountManager = AccountManager.get(context)

    override fun getAuthTokenLabel(authTokenType: String?): String {
        throw UnsupportedOperationException()
    }

    override fun confirmCredentials(response: AccountAuthenticatorResponse?, account: Account?, options: Bundle?): Bundle {
        throw UnsupportedOperationException()
    }

    override fun updateCredentials(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle {
        throw UnsupportedOperationException()
    }

    override fun getAuthToken(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle {
        throw UnsupportedOperationException()
//        accountManager.peekAuthToken(account, authTokenType)?.let {
//            return Bundle().apply {
//                putString(AccountManager.KEY_AUTHTOKEN, it)
//            }
//        }
//
////        val intent = Intent(Intent.ACTION_VIEW).apply {
////            data = Uri.parse(context.getString(R.string.auth_url))
////        }
//
//        val intent = Intent(context, AuthenticatorActivity::class.java)
//        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
//
//        val bundle = Bundle()
//        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
//        return bundle
    }

    override fun hasFeatures(response: AccountAuthenticatorResponse?, account: Account?, features: Array<out String>?): Bundle {
        throw UnsupportedOperationException()
    }

    override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle {
        throw UnsupportedOperationException()
    }

    override fun addAccount(response: AccountAuthenticatorResponse?, accountType: String?, authTokenType: String?, requiredFeatures: Array<out String>?, options: Bundle?): Bundle {
        throw UnsupportedOperationException()
    }

    private fun addAppAccount(accName: String, accType: String) = accountManager.addAccountExplicitly(
            Account(accName, accType),
            null,
            null
    )

    private fun getAppAccount() = accountManager.getAccountsByType(context.getString(R.string.stackoverflow_account_type)).firstOrNull()
}