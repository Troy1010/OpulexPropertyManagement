package com.example.opulexpropertymanagement.models

import android.net.Uri
import android.os.Parcelable
import com.example.opulexpropertymanagement.FBKEY_TENANT
import com.example.opulexpropertymanagement.app.fbUserStorageTable
import com.google.firebase.storage.UploadTask
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tenant(
    val id: String="",
    @SerializedName("landlordid")
    val landlordID: String,
    @SerializedName("propertyid")
    val propertyid: String,
    @SerializedName("tenantaddress")
    val fullAddress: String,
    @SerializedName("tenantemail")
    val email: String,
    @SerializedName("tenantmobile")
    val phone: String,
    @SerializedName("tenantname")
    val name: String
): Parcelable {
    val imageUrlTask
        get() = fbUserStorageTable?.child(FBKEY_TENANT)?.child(id)?.downloadUrl
    fun setImage(uri: Uri): UploadTask? {
        return fbUserStorageTable?.child(FBKEY_TENANT)?.child(id)?.putFile(uri)
    }
}