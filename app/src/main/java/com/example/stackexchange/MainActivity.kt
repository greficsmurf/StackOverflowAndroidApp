package com.example.stackexchange

import android.accounts.Account
import android.accounts.AccountManager
import android.os.Bundle
import android.text.Spanned
import android.webkit.CookieManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.lang.RuntimeException
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

    private val toolbar: Toolbar by lazy{
        findViewById<Toolbar>(R.id.toolbar)
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
                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.logged_in_message), Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        toolbar.setTitle(R.string.home_title)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfig)
        bottomNavBar.setupWithNavController(navController)

        CookieManager.getInstance().setCookie(getString(R.string.stackoverflow_domain), getString(R.string.cookie_string))
    }

    fun setToolbarTitle(title: String){
        toolbar.title = title
    }
    fun setToolbarTitle(title: Spanned){
        toolbar.title = title
    }
    override fun supportFragmentInjector() = dispatchingAndroidInjector
}

