package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.MaintenancesRepo
import com.example.opulexpropertymanagement.models.Maintenance

class MaintenancesVM: ViewModel() {
    val repo = MaintenancesRepo()
    val maintenances = MutableLiveData<ArrayList<Maintenance>>().apply { value = ArrayList() }
    val maintenancesSize = MediatorLiveData<Int>()
    init {
        maintenancesSize.addSource(maintenances) { maintenancesSize.value = it.size }
    }
//    val repo = PropertiesRepo
//    val properties by lazy { MediatorLiveData<List<Property>>() }
//    init {
//        properties.addSource(repo.streamRequestPropertiesResult) {
//            if (it is GetPropertiesResult.Success) {
//                properties.value = it.properties
//            }
//        }
//        properties.addSource(repo.streamAddPropertyResult) {
//            val user = GlobalVM.user.value
//            if ((it is AddPropertyResult.Success) && (user!=null)) {
//                repo.requestPropertiesByUser(user)
//            }
//        }
//        properties.addSource(repo.streamRemovePropertyResult) {
//            val user = GlobalVM.user.value
//            if ((it is RemovePropertyResult.Success) && (user!=null)) {
//                repo.requestPropertiesByUser(user)
//            }
//        }
}