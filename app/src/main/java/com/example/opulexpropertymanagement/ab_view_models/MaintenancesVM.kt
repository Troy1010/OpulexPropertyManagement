package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.MaintenancesRepo
import com.example.opulexpropertymanagement.models.Maintenance

class MaintenancesVM: ViewModel() {
    val repo = MaintenancesRepo()
    var propertyID: String = "NULL" // Must be set by view (might want to change this)
    val maintenances by lazy { MediatorLiveData<List<Maintenance>>() }
    val maintenancesSize = MediatorLiveData<Int>()
    init {
        maintenancesSize.addSource(maintenances) { maintenancesSize.value = it.size }
        maintenances.addSource(repo.streamGetMaintenancesResult) {
            maintenances.value = it
        }
        maintenances.addSource(repo.streamAddMaintenanceResult) {
            if (it) repo.getMaintenances(propertyID) // TODO should probably just directly pass the change here to adjust without re-call
        }
        maintenances.addSource(repo.streamRemoveMaintenanceResult) {
            if (it) repo.getMaintenances(propertyID)
        }
    }
}