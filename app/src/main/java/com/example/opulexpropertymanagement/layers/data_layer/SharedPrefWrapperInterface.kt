package com.example.opulexpropertymanagement.layers.data_layer

import com.example.opulexpropertymanagement.layers.z_ui.User

interface SharedPrefWrapperInterface {
    fun writeUser(user: User?)
    fun readUser(): User?
}