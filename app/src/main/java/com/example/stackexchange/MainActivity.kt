package com.example.stackexchange

import android.accounts.Account
import android.accounts.AccountManager
import android.net.Uri
import android.os.Bundle
import android.webkit.CookieManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }
    private val appBarConfig: AppBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent.data?.let { data ->
            val host: String? = data.host
            if(host == getString(R.string.auth_redirect_url)){
                val token: String? = data.toString().trim().split("access_token=").last()
                if(!token.isNullOrBlank()){
                    val am = AccountManager.get(this)
                    var account = am.getAccountsByType(getString(R.string.stackoverflow_account_type)).firstOrNull()
                    if(account == null){
                        am.addAccountExplicitly(
                                Account(getString(R.string.stackoverflow_account_name), getString(R.string.stackoverflow_account_type)),
                                null, null
                        )
                        account = am.getAccountsByType(getString(R.string.stackoverflow_account_type)).firstOrNull()
                    }
                    am.setAuthToken(account, getString(R.string.stackoverflow_auth_token_type), token)
                }
            }
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        toolbar.setTitle(R.string.home_title)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfig)
        bottomNavBar.setupWithNavController(navController)

        CookieManager.getInstance().setCookie(getString(R.string.stackoverflow_domain), getString(R.string.cookie_string))
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}

