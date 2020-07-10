package com.example.opulexpropertymanagement.models

import android.os.Parcelable
import com.example.opulexpropertymanagement.FBKEY_DOCUMENT
import com.example.opulexpropertymanagement.fbUserStorageTable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Document (
    val id: String,
    val tenantID: String,
    val title: String
): Parcelable {
    val imageUrlTask
        get() = fbUserStorageTable?.child(FBKEY_DOCUMENT)?.child(tenantID)?.child(id)?.downloadUrl
}