package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.PropertiesRepo
import com.example.opulexpropertymanagement.models.Property
import com.example.tmcommonkotlin.logz

class PropertyDetailsVM(properties: MutableLiveData<List<Property>>, i:Int): ViewModel() {
    init {
//        properties.value!!
        logz("properties.value:${properties.value}")
        logz("asdf:${properties.value?.get(i)}")
    }
}