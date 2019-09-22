package com.lebedevsd.githubviewer

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.lebedevsd.githubviewer.base.ui.BaseActivity
import com.lebedevsd.githubviewer.databinding.MainActivityBinding

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {
    override val viewModelClass: Class<MainViewModel> = MainViewModel::class.java
    override val layoutId: Int = R.layout.main_activity

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, null)
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()

        NavigationUI.setupWithNavController(binding.toolbar, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}
