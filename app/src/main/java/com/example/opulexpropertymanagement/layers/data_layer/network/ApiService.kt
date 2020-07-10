package com.example.opulexpropertymanagement.layers.data_layer.network

import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.network_responses.ForgotPasswordResponse
import com.example.opulexpropertymanagement.models.network_responses.PropertiesResponse
import com.example.opulexpropertymanagement.models.network_responses.TenantsResponse
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //(1)  ` Registration
    @GET("pro_mgt_reg.php")
    suspend fun register(
        @Query("email") email: String,
        @Query("landlord_email") landlord_email: String,
        @Query("password") password: String,
        @Query("account_for") userType: String
    ): ResponseBody

    //(2)  ` Login
    @GET("pro_mgt_login.php")
    suspend fun tryLogin(
        @Query("email") email: String,
        @Query("password") password: String
    ): ResponseBody

    //(3)  ` ForgotPassword
    @GET("pro_mgt_forgot_pass.php")
    suspend fun forgotPassword(
        @Query("?email") email: String
    ): ForgotPasswordResponse

    //(4)  ` Property Add
    @GET("pro_mgt_add_pro.php")
    suspend fun addProperty(
        @Query("address") address: String,
        @Query("city") city: String,
        @Query("state") state: String,
        @Query("country") country: String,
        @Query("pro_status") pro_status: String, // What is this?
        @Query("purchase_price") purchase_price: String,
        @Query("mortage_info") mortage_info: String,
        @Query("userid") userid: String,
        @Query("usertype") userType: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): ResponseBody

    //(5)  ` Property List
    @GET("property.php")
    suspend fun getPropertiesForLandlord(
        @Query("usertype") usertype: String,
        @Query("userid") userid: String
    ): PropertiesResponse

    //(6)  ` Remove Property
    @GET("remove-property.php")
    suspend fun removeProperty(
        @Query("propertyid") propertyid: String
    ): ResponseBody

    //(7)   Add Tenants
    @GET("pro_mgt_add_tenants.php")
    suspend fun addTenants(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("address") address: String,
        @Query("mobile") mobile: String,
        @Query("propertyid") propertyid: String,
        @Query("landlordid") landlordid: String
    ): ResponseBody

    //(8)   Contact Tenants // Doesn’t work!!!

    //(9)  ` Tenants Details by landlordid
    @GET("pro_mgt_tenent_details.php")
    suspend fun getTenantsByLandlord(
        @Query("landlordid") landlordid: String
    ): TenantsResponse

    //(10) ` All Property Details for Tenants
    @GET("pro_mgt_property_all.php")
    suspend fun getAllProperties(): List<Property>

}