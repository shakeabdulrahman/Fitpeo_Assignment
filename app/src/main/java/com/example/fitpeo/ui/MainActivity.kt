package com.example.fitpeo.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.example.fitpeo.R
import com.example.fitpeo.base.PrivateSharedPrefManager
import com.example.fitpeo.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var privateSharedPrefManager: PrivateSharedPrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        privateSharedPrefManager = PrivateSharedPrefManager(this)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setupNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp() || navController.navigateUp(appBarConfiguration)
    }

    /* fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    } */

    private fun setupNavigation() {
        toolbar = binding.toolbar
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.mainFragment), drawerLayout)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView!!, navController)
        navigationView!!.setNavigationItemSelectedListener(this)

        navigationView!!.inflateHeaderView(R.layout.navigation_drawer_header)
        //val headerView = navigationView!!.inflateHeaderView(R.layout.navigation_drawer_header)
        //val profileName = headerView.findViewById<TextView>(R.id.profile_name)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}