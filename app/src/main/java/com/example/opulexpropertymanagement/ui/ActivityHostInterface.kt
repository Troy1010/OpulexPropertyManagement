package com.example.opulexpropertymanagement.ui

import androidx.appcompat.widget.Toolbar

interface ActivityHostInterface {
    fun setDrawerEnabled(isEnabled: Boolean)
    val toolbar: Toolbar?
}