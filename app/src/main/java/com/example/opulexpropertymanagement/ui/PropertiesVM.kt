package com.example.opulexpropertymanagement.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.models.Property

class PropertiesVM: ViewModel() {
    val properties by lazy { MutableLiveData<ArrayList<Property>>().apply { value = ArrayList() } }
}