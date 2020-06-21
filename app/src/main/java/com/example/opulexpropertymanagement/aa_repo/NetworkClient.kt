package com.example.opulexpropertymanagement.aa_repo

import com.example.opulexpropertymanagement.app.Config
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.network_responses.ForgotPasswordResponse
import com.example.opulexpropertymanagement.models.network_responses.PropertiesResponse
import com.example.opulexpropertymanagement.models.network_responses.Tenant
import com.example.opulexpropertymanagement.models.network_responses.TenantsResponse
import com.example.tmcommonkotlin.Coroutines
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface INetworkClient {

    //(1)  ` Registration
    @GET("pro_mgt_reg.php")
    fun register(
        @Query("email") email: String,
        @Query("landlord_email") landlord_email: String,
        @Query("password") password: String,
        @Query("account_for") userType: String
    ): Deferred<ResponseBody>

    //(2)  ` Login
    @GET("pro_mgt_login.php")
    fun tryLogin(
        @Query("email") email: String,
        @Query("password") password: String
    ): Deferred<ResponseBody>

    //(3)  ` ForgotPassword
    @GET("pro_mgt_forgot_pass.php")
    fun forgotPassword(
        @Query("?email") email: String
    ): Deferred<ForgotPasswordResponse>

    //(4)  ` Property Add
    @GET("pro_mgt_add_pro.php")
    fun addProperty(
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
    ): Deferred<ResponseBody> // Unsuccessful vs Property

    //(5)  ` Property List
    @GET("property.php")
    fun getPropertiesForLandlord(
        @Query("usertype") usertype: String,
        @Query("userid") userid: String
    ): Deferred<PropertiesResponse>

    //(6)  ` Remove Property
    @GET("remove-property.php")
    fun removeProperty(
        @Query("propertyid") propertyid: String
    ): Deferred<ResponseBody> //succes

    //(7)   Add Tenants
//    GET("http://rjtmobile.com/aamir/property-mgmt/pro_mgt_add_tenants.php?name=aam HYPERLINK \"http://rjtmobile.com/aamir/property-mgmt/pro_mgt_add_tenants.php?name=aam&email=aah@aah.com&address=complte\"& HYPERLINK \"http://rjtmobile.com/aamir/property-mgmt/pro_mgt_add_tenants.php?name=aam&email=aah@aah.com&address=complte\"email=aah@aah.com HYPERLINK \"http://rjtmobile.com/aamir/property-mgmt/pro_mgt_add_tenants.php?name=aam&email=aah@aah.com&address=complte\"& HYPERLINK \"http://rjtmobile.com/aamir/property-mgmt/pro_mgt_add_tenants.php?name=aam&email=aah@aah.com&address=complte\"address=complte address&mobile=9876543210&propertyid=1&landlordid=3")
//    fun addTenant()

    //(8)   Contact Tenants // Doesn’t work!!!

    //(9)  ` Tenants Details by landlordid
    @GET("pro_mgt_tenent_details.php")
    fun getTenantsByLandlord(
        @Query("landlordid") landlordid: String
    ): Deferred<TenantsResponse>

    //(10) ` All Property Details for Tenants
    @GET("pro_mgt_property_all.php")
    fun getAllProperties() : Deferred<List<Property>>

}

val NetworkClient by lazy {
    val gson = GsonBuilder().setLenient().create()
    Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(Config.BASE_URL)
        .build()
        .create(INetworkClient::class.java)
}