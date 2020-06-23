package com.example.opulexpropertymanagement.aa_repo

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.app.FBKEY_MAINTENANCE
import com.example.opulexpropertymanagement.app.FBKEY_PROPERTY
import com.example.opulexpropertymanagement.app.fbUserDBTable
import com.example.opulexpropertymanagement.models.Maintenance
import com.example.tmcommonkotlin.logz
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class MaintenancesRepo {
    // addMaintenance
    val streamAddMaintenanceResult by lazy { MutableLiveData<Boolean>() }
    fun addMaintenance(propertyID: String, maintenance: Maintenance) {
        if (propertyID=="NULL")
            logz("WARNING: Property ID is \"NULL\"")
        val newRef = fbUserDBTable?.child(FBKEY_PROPERTY)?.child(propertyID)?.child(FBKEY_MAINTENANCE)?.push()
        try {
            maintenance.id = newRef?.key!!
        } catch (e: Exception) {
            logz("addMaintenance`WARNING: CAUGHT AN UNTYPED EXCEPTION:${e}")
            logz(" ^This should be fixed once you know the exception types")
            streamAddMaintenanceResult.value = false
            return
        }
        newRef.setValue(maintenance)
            .addOnSuccessListener {
                streamAddMaintenanceResult.value = true
            }
            .addOnFailureListener {
                streamAddMaintenanceResult.value = false
            }
    }

    // removeMaintenance
    val streamRemoveMaintenanceResult by lazy { MutableLiveData<Boolean>() }
    fun removeMaintenance(propertyID: String, maintenance: Maintenance) {
        fbUserDBTable?.child(FBKEY_PROPERTY)?.child(propertyID)?.child(FBKEY_MAINTENANCE)?.child(maintenance.id)
            ?.removeValue()
            ?.addOnSuccessListener {
                streamRemoveMaintenanceResult.value = true
            }
            ?.addOnFailureListener {
                streamRemoveMaintenanceResult.value = false
            }
            ?: run { streamRemoveMaintenanceResult.value = false }
    }

    // updateMaintenance
    val streamUpdateMaintenanceResult by lazy { MutableLiveData<Boolean>() }
    fun updateMaintenance(propertyID: String, maintenance: Maintenance) {
        fbUserDBTable?.child(FBKEY_PROPERTY)?.child(propertyID)?.child(FBKEY_MAINTENANCE)?.child(maintenance.id)
            ?.setValue(maintenance)
            ?.addOnSuccessListener {
                streamUpdateMaintenanceResult.value = true
            }
            ?.addOnFailureListener {
                streamUpdateMaintenanceResult.value = false
            }
            ?: run { streamUpdateMaintenanceResult.value = false }
    }

    // getMaintenances
    val streamGetMaintenancesResult by lazy { MutableLiveData<List<Maintenance>>() }
    fun getMaintenances(propertyID: String) {
        fbUserDBTable?.child(FBKEY_PROPERTY)?.child(propertyID)?.child(FBKEY_MAINTENANCE)
            ?.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) { streamGetMaintenancesResult.value = null }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.value!=null) {
                        logz("dataSnapshot:$dataSnapshot")
                        val maintenances = (dataSnapshot.value as Map<String, Map<String,Maintenance>>).map {
                            it.value
                        }
//                        streamGetMaintenancesResult.value = maintenances
//                        logz("maintenances:${streamGetMaintenancesResult.value}")
                    } else {
                        streamGetMaintenancesResult.value = null
                    }
                }

            })
    }
}