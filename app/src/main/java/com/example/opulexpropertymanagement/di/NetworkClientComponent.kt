package com.example.opulexpropertymanagement.di

import com.example.opulexpropertymanagement.aa.repo.network.INetworkClient
import com.example.opulexpropertymanagement.aa.repo.network.NetworkClient
import com.example.opulexpropertymanagement.app.Config
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.network_responses.ForgotPasswordResponse
import com.example.opulexpropertymanagement.models.network_responses.PropertiesResponse
import com.example.opulexpropertymanagement.models.network_responses.TenantsResponse
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

//@Component
//interface NetworkClientComponent {
//
//    @Binds
//    @Singleton
//    fun provideNetworkClientHolder(): MyHelper
//
//    @Singleton
//    fun provideNetworkClientString(myHelper: MyHelper): String {
//        return myHelper.theNetworkClient
//    }
//}