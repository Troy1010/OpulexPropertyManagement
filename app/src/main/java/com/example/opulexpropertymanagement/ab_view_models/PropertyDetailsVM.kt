package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.PropertiesRepo
import com.example.opulexpropertymanagement.aa_repo.PropertyDetailsRepo
import com.example.opulexpropertymanagement.aa_repo.TenantsRepo
import com.example.opulexpropertymanagement.models.Maintenance
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.Tenant
import com.example.tmcommonkotlin.logz

class PropertyDetailsVM(val properties: MutableLiveData<List<Property>>, i:Int): ViewModel() {
    val propertyDetailsRepo = PropertyDetailsRepo
    val tenantsRepo = TenantsRepo

    val property = properties.value?.get(i)
    val maintenances = ArrayList<Maintenance>()
    val tenant by lazy{ MediatorLiveData<Tenant>() }

    init {
        tenant.addSource(propertyDetailsRepo.streamGetTenantByLandlordAndPropertyResult) { tenant.value = it }
        propertyDetailsRepo.getTenantByLandlordAndPropertyID(GlobalVM.user.value?.id, property?.id)
    }
}