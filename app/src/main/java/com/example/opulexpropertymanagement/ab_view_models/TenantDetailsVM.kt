package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.DocumentsRepo
import com.example.opulexpropertymanagement.aa_repo.PropertiesRepo
import com.example.opulexpropertymanagement.aa_repo.PropertyDetailsRepo
import com.example.opulexpropertymanagement.aa_repo.TenantsRepo
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.models.Tenant
import com.example.tmcommonkotlin.logz

class TenantDetailsVM: ViewModel() {
    val propertyDetailsRepo = PropertyDetailsRepo
    val documentsRepo = DocumentsRepo()

    val tenant by lazy { MediatorLiveData<Tenant>() }
    val documents by lazy { MediatorLiveData<List<Document>>() }

    init {
        tenant.addSource(propertyDetailsRepo.streamGetTenantByLandlordAndPropertyResult) { tenant.value = it }
        val tenantID = tenant.value?.id
        if (tenantID != null) {
            documentsRepo.getDocuments(tenantID)
        } else {
            logz("tenantID was null")
        }
        documents.addSource(documentsRepo.streamGetDocumentsResponse) {
            documents.value = it
        }
        documents.addSource(tenant) {
            documentsRepo.getDocuments(it.id)
        }
    }
}