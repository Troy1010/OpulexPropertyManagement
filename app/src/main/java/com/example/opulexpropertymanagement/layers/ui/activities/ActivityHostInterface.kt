package com.example.opulexpropertymanagement.layers.ui.activities

import androidx.appcompat.widget.Toolbar

interface ActivityHostInterface {
    fun setDrawerEnabled(isEnabled: Boolean)
    fun setToolbarEnabled(isEnabled: Boolean)
    val toolbar: Toolbar?
    fun easyPhoto(code: Int)
    val pickImage: () -> Unit
}