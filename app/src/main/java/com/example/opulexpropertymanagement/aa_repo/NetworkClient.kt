package com.example.opulexpropertymanagement.aa_repo

import com.example.opulexpropertymanagement.app.Config
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface INetworkClient {

    @GET("pro_mgt_reg.php")
    fun register(
        @Query("email") email: String,
        @Query("landlord_email") landlord_email: String,
        @Query("password") password: String,
        @Query("account_for") userType: String
    ): Deferred<ResponseBody>

    @GET("pro_mgt_login.php")
    fun tryLogin(
        @Query("email") email: String,
        @Query("password") password: String
    ): Deferred<ResponseBody>

//    @GET("pro_mgt_add_pro.php")
//    fun addProperty(
//        @Query("")
//    )

}

val NetworkClient by lazy {
    val gson = GsonBuilder().setLenient().create()
    Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(Config.BASE_URL)
        .build()
        .create(INetworkClient::class.java)
}