package com.example.stackexchange

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.main_toolbar.*
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

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        toolbar.setTitle(R.string.home_title)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfig)
        bottomNavBar.setupWithNavController(navController)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}