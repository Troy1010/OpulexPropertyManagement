package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.PropertiesRepo
import com.example.opulexpropertymanagement.aa_repo.PropertyDetailsRepo
import com.example.opulexpropertymanagement.aa_repo.TenantsRepo
import com.example.opulexpropertymanagement.models.Tenant

class TenantDetailsVM: ViewModel() {
    val propertyDetailsRepo = PropertyDetailsRepo

    val tenant by lazy { MediatorLiveData<Tenant>() }

    init {
        tenant.addSource(propertyDetailsRepo.streamGetTenantByLandlordAndPropertyResult) { tenant.value = it }
    }
}