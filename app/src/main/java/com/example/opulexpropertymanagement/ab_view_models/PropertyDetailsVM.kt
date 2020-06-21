package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.PropertiesRepo
import com.example.opulexpropertymanagement.models.Property
import com.example.tmcommonkotlin.logz

class PropertyDetailsVM(val properties: MutableLiveData<List<Property>>, val i:Int): ViewModel() {

    val property = properties.value?.get(i)

    init {
//        properties.value!!
        logz("properties.value:${properties.value}")
        logz("asdf:${properties.value?.get(i)}")
    }
}