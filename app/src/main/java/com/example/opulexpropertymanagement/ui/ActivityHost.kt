package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.opulexpropertymanagement.R
import kotlinx.android.synthetic.main.activity_host.*

class ActivityHost: AppCompatActivity() {
//    val navController by lazy { this.findNavController(R.id.fragNavHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        val navController = findNavController(R.id.fragNavHost)

        // Toolbar
        setSupportActionBar(toolbar_main)
        setupActionBarWithNavController(navController)
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
}