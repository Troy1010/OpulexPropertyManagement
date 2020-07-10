package com.example.opulexpropertymanagement.layers.z_ui.extras

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.opulexpropertymanagement.layers.view_models.PropertyDetailsVM
import com.example.opulexpropertymanagement.models.Property


class PropertyDetailsVMFactory(private val properties: MutableLiveData<List<Property>>, val i:Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PropertyDetailsVM(properties, i) as T
    }
}