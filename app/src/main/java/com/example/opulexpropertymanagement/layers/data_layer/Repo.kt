package com.example.opulexpropertymanagement.layers.data_layer

import com.example.opulexpropertymanagement.layers.z_ui.User
import com.example.tmcommonkotlin.logz
import javax.inject.Inject

class Repo @Inject constructor(private val sharedPrefWrapper: SharedPrefWrapper) {
    fun readUser() : User? {
        return sharedPrefWrapper.getUserFromSharedPref()
    }
    fun writeUser(user:User?) {
        sharedPrefWrapper.saveUserInSharedPref(user)
    }
}