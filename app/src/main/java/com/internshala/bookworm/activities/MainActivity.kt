package com.internshala.bookworm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.internshala.bookworm.fragments.AboutAppFragment
import com.internshala.bookworm.fragments.DashboardFragment
import com.internshala.bookworm.fragments.FavouriteFragment
import com.internshala.bookworm.fragments.myProfileFragment
import com.internshala.bookworm.R

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolBar: androidx.appcompat.widget.Toolbar
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var navigationDrawer: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        toolBar = findViewById(R.id.toolBar)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        navigationDrawer = findViewById(R.id.navigationDrawer)
        setUpToolBar()
        val actionBarDrawerToogle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToogle)
        actionBarDrawerToogle.syncState()
        openDashboard()
        navigationDrawer.setNavigationItemSelectedListener {
            it.isCheckable = true
            it.isChecked = true
            when (it.itemId) {
                R.id.dashboard -> {
                    openDashboard()
                }
                R.id.myProfile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            myProfileFragment()
                        )
                        .commit()
                    supportActionBar?.title = "myProfile"
                    drawerLayout.closeDrawers()
                }
                R.id.Favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            FavouriteFragment()
                        )
                        .commit()
                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.aboutApp -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            AboutAppFragment()
                        )
                        .commit()
                    supportActionBar?.title ="About"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolBar() {
        setSupportActionBar(toolBar)
        supportActionBar?.title = "Action Bar"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    fun openDashboard(){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame,
                DashboardFragment()
            )
            .commit()

        supportActionBar?.title = "Dashboard"
        navigationDrawer.setCheckedItem(R.id.dashboard)
        drawerLayout.closeDrawers()
    }

    override fun onBackPressed() {
        val flag = supportFragmentManager?.findFragmentById(R.id.frame)
        if(flag == DashboardFragment()){
            super.onBackPressed()
        }else{
            openDashboard()
            navigationDrawer.setCheckedItem(R.id.dashboard)
        }
    }
}