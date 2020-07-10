package com.example.opulexpropertymanagement.di

import com.example.opulexpropertymanagement.BASE_URL
import com.example.opulexpropertymanagement.layers.data_layer.Repo
import com.example.opulexpropertymanagement.layers.data_layer.SharedPrefWrapper
import com.example.opulexpropertymanagement.layers.data_layer.network.INetworkClient
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModuleZ {
//    @Provides
//    @Singleton
//    fun providesSharedPrefWrapper(): SharedPrefWrapper {
//        return S
//    }

    @Provides
    @Singleton
    fun providesRepo(sharedPrefWrapper: SharedPrefWrapper): Repo {
        return Repo(sharedPrefWrapper)
    }

    @Provides
    @Singleton
    fun provideNetworkClient(): INetworkClient {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(INetworkClient::class.java)
    }
}