package com.example.opulexpropertymanagement.di


import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.network_responses.ForgotPasswordResponse
import com.example.opulexpropertymanagement.models.network_responses.PropertiesResponse
import com.example.opulexpropertymanagement.models.network_responses.TenantsResponse
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody

interface ApiHelper {

    //(1)  ` Registration
    fun register(
        email: String,
        landlord_email: String,
        password: String,
        userType: String
    ): Deferred<ResponseBody>

    //(2)  ` Login
    fun tryLogin(
        email: String,
        password: String
    ): Deferred<ResponseBody>

    //(3)  ` ForgotPassword
    fun forgotPassword(
        email: String
    ): Deferred<ForgotPasswordResponse>

    //(4)  ` Property Add
    fun addProperty(
        address: String,
        city: String,
        state: String,
        country: String,
        pro_status: String, // What is this?
        purchase_price: String,
        mortage_info: String,
        userid: String,
        userType: String,
        latitude: String,
        longitude: String
    ): Deferred<ResponseBody> // Unsuccessful vs Property

    //(5)  ` Property List
    fun getPropertiesForLandlord(
        usertype: String,
        userid: String
    ): Deferred<PropertiesResponse>

    //(6)  ` Remove Property
    fun removeProperty(
        propertyid: String
    ): Deferred<ResponseBody> //succes

    //(7)   Add Tenants
    fun addTenants(
        name: String,
        email: String,
        address: String,
        mobile: String,
        propertyid: String,
        landlordid: String
    ): Deferred<ResponseBody>
    fun getTenantsByLandlord(
        landlordid: String
    ): Deferred<TenantsResponse>
    fun getAllProperties() : Deferred<List<Property>>

}