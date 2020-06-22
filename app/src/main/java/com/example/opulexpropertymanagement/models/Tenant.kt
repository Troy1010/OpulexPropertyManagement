package com.example.opulexpropertymanagement.models

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
)