package com.example.opulexpropertymanagement.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.opulexpropertymanagement.BASE_URL
import com.example.opulexpropertymanagement.SHARED_PREF_FILE_NAME
import com.example.opulexpropertymanagement.layers.data_layer.Repo
import com.example.opulexpropertymanagement.layers.data_layer.SharedPrefWrapper
import com.example.opulexpropertymanagement.layers.data_layer.network.ApiService
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
class AppModule(private val appContext: Application?=null) {
    @Provides
    @Singleton
    fun providesAppContext() : Context = appContext!!

    @Provides
    @Singleton
    fun providesSharedPreferences(appContext: Context) : SharedPreferences {
        return appContext.getSharedPreferences(
            SHARED_PREF_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun providesRepo(sharedPrefWrapper: SharedPrefWrapper): Repo {
        return Repo(sharedPrefWrapper)
    }

    @Provides
    @Singleton
    fun provideNetworkClient(): ApiService {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)
    }
}