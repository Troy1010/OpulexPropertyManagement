package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.PropertiesRepo
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.opulexpropertymanagement.models.streamable.GetPropertiesResult

// meant to live in activityViewModels()
//  Both FragProperties and FragPropertyAdd need access to it..
class PropertiesVM: ViewModel() {
    val repo = PropertiesRepo
    val properties by lazy { MediatorLiveData<List<Property>>() }
    init {
        properties.addSource(repo.streamRequestPropertiesResult) {
            if (it is GetPropertiesResult.Success) {
                properties.value = it.properties
            }
        }
        properties.addSource(repo.streamAddPropertyResult) {
            if (it is AddPropertyResult.Success) {
                repo.requestPropertiesByUser(it.user)
            }
        }
        GlobalVM.user.value?.let { repo.requestPropertiesByUser(it) }
    }
}