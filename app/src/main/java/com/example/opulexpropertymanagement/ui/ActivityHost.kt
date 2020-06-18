package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.repo.SharedPref
import com.example.opulexpropertymanagement.view_models.UserVM
import kotlinx.android.synthetic.main.activity_host.*

class ActivityHost : AppCompatActivity(), ActivityHostInterface {
    lateinit var drawerToggle: ActionBarDrawerToggle
    override val toolbar: Toolbar? by lazy { toolbar_main }
    val navController by lazy { findNavController(R.id.fragNavHost) }
    val userVM: UserVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        // Toolbar
        toolbar?.title = navController.currentDestination?.label // for some reason this is required on first page
        setSupportActionBar(toolbar_main)
        // Drawer
        drawer_view.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawer_layout.closeDrawers()
            when (menuItem.itemId) {
                R.id.menuitem_properties -> navController.navigate(R.id.action_global_fragProperties)
                R.id.menuitem_home -> navController.navigate(R.id.action_global_fragHome)
            }
            true
        }
        // Drawer + Toolbar
        drawerToggle =
            ActionBarDrawerToggle(this, drawer_layout, toolbar_main, R.string.open, R.string.closed)
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        // Keep user hot, save user changes in SharedPref
        userVM.user.observe(this, Observer {
            SharedPref.saveUserInSharedPref(it)
        })

    }

    override fun onBackPressed() {
        // Drawer
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Toolbar
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun setDrawerEnabled(isEnabled: Boolean) {
        val lockMode =
            if (isEnabled) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        drawer_layout.setDrawerLockMode(lockMode)
        drawerToggle.isDrawerIndicatorEnabled = isEnabled
        drawerToggle.syncState()
    }

    override fun setToolbarEnabled(isEnabled: Boolean) {
        if (isEnabled) {
            toolbar_main.visibility = View.VISIBLE
        } else {
            toolbar_main.visibility = View.GONE
        }
    }
}