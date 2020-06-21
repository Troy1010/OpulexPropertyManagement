package com.example.opulexpropertymanagement.ac_ui.extras

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.opulexpropertymanagement.ab_view_models.PropertyDetailsVM
import com.example.opulexpropertymanagement.models.Property


class PropertyDetailsVMFactory(private val properties: MutableLiveData<List<Property>>, val i:Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PropertyDetailsVM(properties, i) as T
    }
}