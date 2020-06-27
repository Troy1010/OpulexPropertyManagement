package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.navigation.NavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.aa_repo.DocumentsRepo
import com.example.opulexpropertymanagement.ab_view_models.inheritables.FragmentallyScopedVM
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.models.streamable.AddDocumentResult
import com.example.opulexpropertymanagement.models.streamable.UpdateDocumentResult
import com.example.tmcommonkotlin.logz
var tenantDetailsVM : FragmentallyScopedVM?=null
fun getTenantVM(navController:NavController): TenantDetailsVM {
    if (tenantDetailsVM==null) {
        tenantDetailsVM = TenantDetailsVM(navController)
    }
    return tenantDetailsVM as TenantDetailsVM
}
class TenantDetailsVM(val navController: NavController): FragmentallyScopedVM(navController, ::tenantDetailsVM) {
    override val fragmentsToScopeWith = hashSetOf(R.id.tenantDetailsFrag, R.id.documentDetailsFrag)
    val documentsRepo = DocumentsRepo()

    val tenant by lazy { MediatorLiveData<Tenant>() }
    val documents by lazy { MediatorLiveData<List<Document>>() }

    init {
        logz("TenantDetailsVM`Init`Open")
        documents.addSource(documentsRepo.streamGetDocumentsResponse) {
            documents.value = it
        }
        documents.addSource(tenant) {
            documentsRepo.getDocuments(it.id)
        }
        documents.addSource(documentsRepo.removeDocumentResult) {
            documentsRepo.getDocuments(it.document.tenantID)
        }
        documents.addSource(documentsRepo.updateDocumentResult) {
            if (it is UpdateDocumentResult.Success)
                documentsRepo.getDocuments(it.document.tenantID)
        }
        documents.addSource(documentsRepo.addDocumentResult) {
            if (it is AddDocumentResult.Success)
                documentsRepo.getDocuments(it.document.tenantID)
        }
    }

    fun finalize() {
        logz("TenantDetailsVM`finalize")
    }
}