package com.example.opulexpropertymanagement.repo.network

import com.example.opulexpropertymanagement.app.Config
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.reactivex.Observable
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.reflect.Type


interface INetworkClient {

    @GET("pro_mgt_reg.php")
    fun register(
        @Query("email") email: String,
        @Query("landlord_email") landlord_email: String,
        @Query("password") password: String,
        @Query("account_for") userType: String
    ): Observable<String>

//    fun register(email: String, password:String, userType: UserType): String {
//        return register(email, password, userType.name)
//    }

    @GET("pro_mgt_login.php?email={email}&password={password}")
    fun login(
        @Path("email") email: String,
        @Path("password") password: String
    ): String

//    @GET("pro_mgt_forgot_pass.php?email=aa@aa.com")
//    fun forgotPassword(
//        @Path()
//    )

//    @GET("/api/subcategory/{id}")
//    fun getSubCategoriesObservable(@Path("id") id: Int) : Observable<ReceivedSubCategories>
}

val NetworkClient by lazy {
    val gson = GsonBuilder().setLenient().create()
    Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(Config.BASE_URL)
        .build()
        .create(INetworkClient::class.java)
}