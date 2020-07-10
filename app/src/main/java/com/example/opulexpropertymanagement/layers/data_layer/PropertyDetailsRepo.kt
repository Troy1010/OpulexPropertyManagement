package com.example.opulexpropertymanagement.layers.data_layer

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.layers.data_layer.network.NetworkClient
import com.example.opulexpropertymanagement.models.Tenant
import com.example.tmcommonkotlin.Coroutines

object PropertyDetailsRepo {

    val streamGetTenantByLandlordAndPropertyResult by lazy { MutableLiveData<Tenant>() }
    fun getTenantByLandlordAndPropertyID(landlordID: String?, propertyID: String?) {
        if (landlordID==null || propertyID==null)
            return
        Coroutines.ioThenMain(
            { NetworkClient.getTenantsByLandlord(landlordID).await().Tenants.find { tenant -> tenant.propertyid == propertyID } },
            { streamGetTenantByLandlordAndPropertyResult.value = it }
        )
    }
}