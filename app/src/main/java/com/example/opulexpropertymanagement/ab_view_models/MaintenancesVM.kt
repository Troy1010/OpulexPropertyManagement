package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.MaintenancesRepo
import com.example.opulexpropertymanagement.models.Maintenance
import com.example.opulexpropertymanagement.models.Property

class MaintenancesVM(property: Property): ViewModel() {
    val repo = MaintenancesRepo()
    var propertyID: String = property.id
    val maintenances = MediatorLiveData<List<Maintenance>>()
    val maintenancesSize = MediatorLiveData<Int>()
    val selectedMaintenance by lazy { MutableLiveData<Maintenance>() }
    init {
        maintenances.value = emptyList()
        maintenancesSize.addSource(maintenances) { maintenancesSize.value = it?.size?:0 }
        maintenances.addSource(repo.streamGetMaintenancesResult) {
            maintenances.value = it
        }
        maintenances.addSource(repo.streamAddMaintenanceResult) {
            if (it) repo.getMaintenances(propertyID) // TODO should probably just directly pass the change here to adjust without re-call
        }
        maintenances.addSource(repo.streamRemoveMaintenanceResult) {
            if (it) repo.getMaintenances(propertyID)
        }
        maintenances.addSource(repo.streamUpdateMaintenanceResult) {
            if (it) repo.getMaintenances(propertyID)
        }
        repo.getMaintenances(propertyID)
    }
}