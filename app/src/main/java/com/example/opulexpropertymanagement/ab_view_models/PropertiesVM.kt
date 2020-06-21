package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.PropertiesRepo
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.opulexpropertymanagement.models.streamable.GetPropertiesResult
import com.example.opulexpropertymanagement.models.streamable.RemovePropertyResult

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
            val user = GlobalVM.user.value
            if ((it is AddPropertyResult.Success) && (user!=null)) {
                repo.requestPropertiesByUser(user)
            }
        }
        properties.addSource(repo.streamRemovePropertyResult) {
            val user = GlobalVM.user.value
            if ((it is RemovePropertyResult.Success) && (user!=null)) {
                repo.requestPropertiesByUser(user)
            }
        }
//        properties.addSource(repo.streamRemovePropertyResult) {
//            val user = GlobalVM.user.value
//            if ((it is RemovePropertyResult.Success) && (user!=null)) {
//                repo.requestPropertiesByUser(user)
//            }
//        }
        GlobalVM.user.value?.let { repo.requestPropertiesByUser(it) }
    }
}