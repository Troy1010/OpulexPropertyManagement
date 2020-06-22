package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.PropertiesRepo
import com.example.opulexpropertymanagement.aa_repo.PropertyDetailsRepo
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.Tenant
import com.example.tmcommonkotlin.logz

class PropertyDetailsVM(val properties: MutableLiveData<List<Property>>, i:Int): ViewModel() {
    val propertyDetailsRepo = PropertyDetailsRepo()

    val property = properties.value?.get(i)
    val tenant by lazy{ MediatorLiveData<Tenant>() }

    init {
        propertyDetailsRepo.getTenantByLandlordAndPropertyID(GlobalVM.user.value?.id, property?.id)
        tenant.addSource(propertyDetailsRepo.streamGetTenantByLandlordAndPropertyResult) { tenant.value = it }
    }
}