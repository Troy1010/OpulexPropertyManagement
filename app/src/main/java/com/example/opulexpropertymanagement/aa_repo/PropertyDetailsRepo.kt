package com.example.opulexpropertymanagement.aa_repo

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.models.network_responses.Tenant
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.tmcommonkotlin.Coroutines

class PropertyDetailsRepo {

    val streamGetTenantByLandlordAndPropertyResult by lazy { MutableLiveData<Tenant>() }
    fun getTenantByLandlordAndPropertyID(landlordID: String?, propertyID: String?) {
        if (landlordID==null || propertyID==null)
            return
        Coroutines.ioThenMain(
            { NetworkClient.getTenantsByLandlord(landlordID).await().Tenants.find { tenant -> tenant.propertyid == propertyID } },
            { it?.let {streamGetTenantByLandlordAndPropertyResult.value = it} }
        )
    }
}