package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.ab_view_models.inheritables.IJobStopper
import com.example.opulexpropertymanagement.ab_view_models.inheritables.JobStopper
import com.example.opulexpropertymanagement.models.Property

class PropertiesVM: ViewModel() {
    val properties by lazy { MutableLiveData<ArrayList<Property>>().apply { value = ArrayList() } }
}