package com.example.opulexpropertymanagement.ui

import androidx.appcompat.widget.Toolbar

interface ActivityHostInterface {
    fun setDrawerEnabled(isEnabled: Boolean)
    fun setToolbarEnabled(isEnabled: Boolean)
    val toolbar: Toolbar?
}