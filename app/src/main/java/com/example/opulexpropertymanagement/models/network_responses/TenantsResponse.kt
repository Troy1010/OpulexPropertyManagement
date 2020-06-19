package com.example.opulexpropertymanagement.models.network_responses

data class TenantsResponse(
    val Tenants: List<Tenant>
)

data class Tenant(
    val id: String,
    val landlordid: String,
    val propertyid: String,
    val tenantaddress: String,
    val tenantemail: String,
    val tenantmobile: String,
    val tenantname: String
)