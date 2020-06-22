package com.example.opulexpropertymanagement.models

import com.example.opulexpropertymanagement.app.FBKEY_TENANT
import com.example.opulexpropertymanagement.app.fbUserStorageTable
import com.google.gson.annotations.SerializedName

data class Tenant(
    val id: String,
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
) {
    val imageUrlTask
        get() = fbUserStorageTable?.child(FBKEY_TENANT)?.child(id)?.downloadUrl
}