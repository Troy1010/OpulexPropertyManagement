package com.example.opulexpropertymanagement.aa.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.grocerygo.activities_and_frags.Inheritables.TMActivity
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.aa.view_models.GlobalVM
import com.example.opulexpropertymanagement.aa.ui.GlobalRepo
import com.example.opulexpropertymanagement.aa.view_models.PropertiesVM
import com.example.opulexpropertymanagement.aa.view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.app.*
import com.example.opulexpropertymanagement.util.scopeVMToDestinations
import kotlinx.android.synthetic.main.activity_host.*

class ActivityHost : TMActivity(),
    ActivityHostInterface {
    lateinit var drawerToggle: ActionBarDrawerToggle
    override val toolbar: Toolbar? by lazy { toolbar_main }
    val navController by lazy { findNavController(R.id.fragNavHost) }

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
                R.id.menuitem_login -> navController.navigate(R.id.action_global_fragLogin)
                R.id.menuitem_tenants -> navController.navigate(R.id.action_global_tenantsFrag)
            }
            true
        }
        // Drawer + Toolbar
        drawerToggle =
            ActionBarDrawerToggle(this, drawer_layout, toolbar_main, R.string.open, R.string.closed)
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        // Keep user hot & save user changes in SharedPref & sync to correct fbTable
        GlobalVM.user.observe(this, Observer {
            GlobalRepo.sharedPref.saveUserInSharedPref(it)
            it?.let { fbUserDBTable = firebaseDB.getReference(it.id) }
            it?.let { fbUserStorageTable = firebaseStorage.getReference(it.id) }
        })
        // If we don't have a user, start at TenantOrLandlord
        if (GlobalVM.user.value == null) {
            navController.navigate(R.id.action_fragProperties_to_fragTenantOrLandlord)
        }
        // ?
//        this.scopeVMToDestinations<PropertiesVM>(navController, hashSetOf(R.id.fragProperties, R.id.fragPropertyAdd))
        this.scopeVMToDestinations<TenantDetailsVM>(navController, hashSetOf(R.id.tenantDetailsFrag, R.id.documentDetailsFrag))
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

    // image

    override val pickImage = {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Config.CODE_PICK_IMAGE)
    }
}