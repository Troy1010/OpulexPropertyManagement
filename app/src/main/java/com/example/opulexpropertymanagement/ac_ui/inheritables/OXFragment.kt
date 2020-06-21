package com.example.opulexpropertymanagement.ac_ui.inheritables

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.ac_ui.activities.ActivityHostInterface

open class OXFragment(val isDrawerEnabled: Boolean = true, val isToolbarEnabled: Boolean = true): Fragment() {

    override fun onStart() {
        super.onStart()
        val activityTemp = activity
        if (activityTemp is ActivityHostInterface) {
            activityTemp.setDrawerEnabled(isDrawerEnabled)
            activityTemp.setToolbarEnabled(isToolbarEnabled)
            activityTemp.toolbar?.title = this.findNavController().currentDestination?.label
        }
    }
}