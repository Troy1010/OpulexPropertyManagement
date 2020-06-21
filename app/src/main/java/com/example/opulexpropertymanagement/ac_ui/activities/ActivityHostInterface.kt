package com.example.opulexpropertymanagement.ac_ui.activities

import android.content.Intent
import androidx.appcompat.widget.Toolbar

interface ActivityHostInterface {
    fun setDrawerEnabled(isEnabled: Boolean)
    fun setToolbarEnabled(isEnabled: Boolean)
    val toolbar: Toolbar?
    fun easyPhoto(code: Int)
    val pickImage: () -> Unit
}