package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.opulexpropertymanagement.R
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.activity_host.*

class ActivityHost : AppCompatActivity(), ActivityHostInterface {
//    val navController by lazy { this.findNavController(R.id.fragNavHost) }
    val drawerToggle by lazy {ActionBarDrawerToggle(this, drawer_layout, toolbar_main, R.string.open, R.string.closed)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        val navController = findNavController(R.id.fragNavHost)

        // Toolbar
        setSupportActionBar(toolbar_main)
        setupActionBarWithNavController(navController)
        // Drawer
        logz("closingDrawer..")
        drawer_layout.closeDrawers()
        drawer_nav_view.setNavigationItemSelectedListener {
            it.isChecked = true
            logz("closingDrawer..")
            drawer_layout.closeDrawers()
            true
        }
        // Drawer + Toolbar
        drawer_layout.addDrawerListener(drawerToggle)
//        toolbar_main.setNavigationIcon(R.drawable.ic_launcher_background);
//        val mDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.hello_world, R.string.hello_world)
//        {
//
//            public void onDrawerClosed(View view)
//            {
//                supportInvalidateOptionsMenu();
//                //drawerOpened = false;
//            }
//
//            public void onDrawerOpened(View drawerView)
//            {
//                supportInvalidateOptionsMenu();
//                //drawerOpened = true;
//            }
//        };
//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//        drawerLayout.setDrawerListener(mDrawerToggle);
//        mDrawerToggle.syncState();
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        drawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val returning = super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
            }
        }
        return returning
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun setDrawerEnabled(isEnabled: Boolean) {
        val lockMode = if (isEnabled) DrawerLayout.LOCK_MODE_UNLOCKED
        else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        drawer_layout.setDrawerLockMode(lockMode)
    }
}