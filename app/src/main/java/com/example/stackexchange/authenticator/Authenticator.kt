package com.example.stackexchange.authenticator

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import com.example.stackexchange.R
import com.example.stackexchange.base.BaseApplication
import timber.log.Timber
import java.lang.Exception
import java.lang.UnsupportedOperationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StackOverflowAuthenticator @Inject constructor(val application: BaseApplication){
    private val mAccountManager = AccountManager.get(application.applicationContext)

    private val mAccType = application.getString(R.string.stackoverflow_account_type)
    private val mAuthType = application.getString(R.string.stackoverflow_auth_token_type)
    private val mAccName = application.getString(R.string.stackoverflow_account_name)
    private val mAuthUrl = application.getString(R.string.auth_url)

    /**
     * Gets cached stackoverflow auth token, null if doesn't exist
     * null if stackoverflow account doesn't exist
     */
    fun getAuthToken(): String?{
        return try{
            mAccountManager.peekAuthToken(
                    mAccountManager.getAccountsByType(mAccType).firstOrNull(), mAuthType
            )
        }catch (e: Exception) {
            null
        }
    }

    /**
     * Starts browser to get stackoverflow token
     */
    fun logIn(){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(mAuthUrl)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(application, intent, null)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun logOff(){
        mAccountManager.getAccountsByType(mAccType).firstOrNull()?.let {
            mAccountManager.removeAccountExplicitly(it)
        }
    }

    /**
     * Creates stackoverflow account
     */
    fun createAccount() = mAccountManager.addAccountExplicitly(Account(mAccName, mAccType),null,null)

}