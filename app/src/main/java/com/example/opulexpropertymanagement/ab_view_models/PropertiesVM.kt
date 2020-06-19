package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.models.Property

class PropertiesVM: ViewModel() {
    val properties by lazy { MutableLiveData<ArrayList<Property>>().apply { value = ArrayList() } }
    fun addProperty(property: Property, user: User) {
        Repo.addProperty(property, user)
    }
}