package com.example.opulexpropertymanagement.repo.network

import com.example.opulexpropertymanagement.app.Config
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.ui.User
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface INetworkClient {

    @GET("pro_mgt_reg.php?&email={email}&landlord_email={email}&password={password}&account_for={userType}")
    fun register(
        @Path("email") email: String,
        @Path("password") password: String,
        @Path("userType") userType: String
    ): String
    fun register(email: String, password:String, userType: UserType): String {
        return register(email, password, userType.name)
    }

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
    Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(Config.BASE_URL)
        .build()
        .create(INetworkClient::class.java)
}