package com.example.opulexpropertymanagement.layers.view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.layers.data_layer.PropertyDetailsRepo
import com.example.opulexpropertymanagement.layers.data_layer.TenantsRepo
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.Tenant

class PropertyDetailsVM(val properties: MutableLiveData<List<Property>>, i:Int): ViewModel() {
    val propertyDetailsRepo = PropertyDetailsRepo
    val tenantsRepo = TenantsRepo

    val property = properties.value?.get(i)
    val tenant by lazy{ MediatorLiveData<Tenant>() }

    init {
        tenant.addSource(propertyDetailsRepo.streamGetTenantByLandlordAndPropertyResult) { tenant.value = it }
        propertyDetailsRepo.getTenantByLandlordAndPropertyID(GlobalVM.user.value?.id, property?.id)
    }
}