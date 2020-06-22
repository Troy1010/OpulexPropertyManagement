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
import com.example.opulexpropertymanagement.models.streamable.AddDocumentResult
import com.example.opulexpropertymanagement.models.streamable.UpdateDocumentResult
import com.example.tmcommonkotlin.logz

class TenantDetailsVM : ViewModel() {
    val documentsRepo = DocumentsRepo()

    val tenant by lazy { MediatorLiveData<Tenant>() }
    val documents by lazy { MediatorLiveData<List<Document>>() }

    init {
        documents.addSource(documentsRepo.streamGetDocumentsResponse) {
            logz("Updating documents..")
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
}