package com.example.opulexpropertymanagement.models.network_responses

import com.example.opulexpropertymanagement.models.Tenant
import com.google.gson.annotations.SerializedName

data class TenantsResponse(
    val Tenants: List<Tenant>
)