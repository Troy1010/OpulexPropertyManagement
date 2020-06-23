package com.example.opulexpropertymanagement.aa_repo

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.app.FBKEY_MAINTENANCE
import com.example.opulexpropertymanagement.app.fbUserDBTable
import com.example.opulexpropertymanagement.models.Maintenance
import com.example.tmcommonkotlin.logz
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class MaintenancesRepo {
    // addDocument
    val streamAddMaintenanceResult by lazy { MutableLiveData<Boolean>() }
    fun addMaintenance(propertyID: String, maintenance: Maintenance) {
        val newRef = fbUserDBTable?.child(propertyID)?.child(FBKEY_MAINTENANCE)?.push()
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

    val streamRemoveMaintenanceResult by lazy { MutableLiveData<Boolean>() }
    fun removeMaintenance(propertyID: String, maintenance: Maintenance) {
        fbUserDBTable?.child(propertyID)?.child(FBKEY_MAINTENANCE)?.child(maintenance.id)?.removeValue()
            ?.addOnSuccessListener {
                streamRemoveMaintenanceResult.value = true
            }
            ?.addOnFailureListener {
                streamRemoveMaintenanceResult.value = false
            }
            ?: run { streamRemoveMaintenanceResult.value = false }
    }

    val streamGetMaintenancesResult by lazy { MutableLiveData<List<Maintenance>>() }
    fun getMaintenances(propertyID: String) {
        fbUserDBTable?.child(propertyID)?.child(FBKEY_MAINTENANCE)
            ?.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) { streamGetMaintenancesResult.value = null }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.value!=null) {
                        val maintenances = (dataSnapshot.value as Map<String, Maintenance>).map {
                            it.value
                        }
                        streamGetMaintenancesResult.value = maintenances
                        logz("maintenances:${streamGetMaintenancesResult.value}")
                    } else {
                        streamGetMaintenancesResult.value = null
                    }
                }

            })
    }
}