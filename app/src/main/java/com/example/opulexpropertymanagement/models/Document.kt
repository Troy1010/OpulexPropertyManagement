package com.example.opulexpropertymanagement.models

import com.example.opulexpropertymanagement.app.FBKEY_DOCUMENT
import com.example.opulexpropertymanagement.app.fbUserStorageTable

data class Document (
    val id: String,
    val tenantID: String,
    val title: String
) {
    val imageUrlTask
        get() = fbUserStorageTable?.child(FBKEY_DOCUMENT)?.child(tenantID)?.child(id)?.downloadUrl
}