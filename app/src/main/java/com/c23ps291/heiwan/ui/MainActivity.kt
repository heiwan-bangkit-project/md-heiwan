package com.c23ps291.heiwan.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.c23ps291.heiwan.R
import com.c23ps291.heiwan.databinding.ActivityMainBinding
import com.c23ps291.heiwan.ui.common.ImageChooseBottomSheetFragment
import com.c23ps291.heiwan.ui.search.SearchActivity
import com.c23ps291.heiwan.utils.createCustomTempFile
import com.c23ps291.heiwan.utils.uriToFile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var modalBottomSheet: ImageChooseBottomSheetFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val drawerView: NavigationView = binding.navDrawerView
        val navView: BottomNavigationView = binding.appBarMain.botNavView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_bookmark,
                R.id.navigation_profile,
                R.id.navigation_community,
                R.id.navigation_setting
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        drawerView.setNavigationItemSelectedListener { menuItem ->
            drawerView.setupWithNavController(navController)

            menuItem.isChecked = true
            drawerLayout.close()
            true
        }

        modalBottomSheet = ImageChooseBottomSheetFragment()

        binding.appBarMain.apply {
            btnSearch.setOnClickListener {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
            btnCamera.setOnClickListener {
                modalBottomSheet.show(supportFragmentManager, ImageChooseBottomSheetFragment.TAG)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}