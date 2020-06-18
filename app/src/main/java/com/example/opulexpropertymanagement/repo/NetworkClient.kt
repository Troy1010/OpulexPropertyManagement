package com.example.opulexpropertymanagement.repo

import com.example.opulexpropertymanagement.app.Config
import com.example.opulexpropertymanagement.ui.User
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
    ): Call<String>

    @GET("pro_mgt_login.php")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Deferred<User>

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