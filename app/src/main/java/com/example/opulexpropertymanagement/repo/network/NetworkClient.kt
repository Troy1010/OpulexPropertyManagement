package com.example.opulexpropertymanagement.repo.network

import com.example.pg_mvvm.app.Config
import com.example.pg_mvvm.models.User
import com.example.pg_mvvm.models.network_responses.LoginResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface INetworkClient {

    @POST("auth/login")
    fun login(@Body user: User) : Observable<LoginResponse>

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