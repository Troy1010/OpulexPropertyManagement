package com.example.opulexpropertymanagement.ui.inheritables

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.ui.ActivityHostInterface
import com.example.tmcommonkotlin.logz

open class OXFragment(val isDrawerEnabled: Boolean = true): Fragment() {

    override fun onStart() {
        super.onStart()
        val activityTemp = activity
        if (activityTemp is ActivityHostInterface) {
            activityTemp.setDrawerEnabled(isDrawerEnabled)
            activityTemp.toolbar?.title = this.findNavController().currentDestination?.label
        }
    }
}