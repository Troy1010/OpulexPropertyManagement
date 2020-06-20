package com.example.opulexpropertymanagement.ac_ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.opulexpropertymanagement.aa_repo.NetworkClient
import com.example.opulexpropertymanagement.aa_repo.SharedPref
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.opulexpropertymanagement.models.streamable.RegisterResult
import com.example.tmcommonkotlin.Coroutines
import com.example.tmcommonkotlin.log
import com.example.tmcommonkotlin.logSubscribe
import com.example.tmcommonkotlin.logz
import com.google.gson.Gson
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


object Repo {
    // SharedPref
    val sharedPref = SharedPref

    // Network
    //  TryLogin
    val liveDataTryLogin by lazy { MutableLiveData<TryLoginResult>() } // Observed by multiple VMs

    fun tryLogin(email: String, password: String) {
        Coroutines.ioThenMain(
            {
                val responseString = NetworkClient.tryLogin(email, password).await().string()
                if ("success" in responseString) {
                    val user = Gson().fromJson(responseString, User::class.java)
                    TryLoginResult.Success(user)
                } else {
                    TryLoginResult.Failure("Unknown error")
                }
            },
            {
                liveDataTryLogin.value = it
            }
        )
    }

    suspend fun register(email: String, password: String, userType: UserType): RegisterResult {
        val resultString = NetworkClient.register(email, email, password, userType.name)
            .await().string()
        if ("success" in resultString) {
            tryLogin(email, password)
            return RegisterResult.Success
        } else if ("Email already exsist" in resultString) {
            return RegisterResult.Failure.EmailAlreadyExists
        } else {
            return RegisterResult.Failure.Unknown
        }
    }

    val streamAddProperty by lazy { PublishSubject.create<AddPropertyResult>() }
    fun addProperty(property: Property, user: User) {
        logz("add property..")
        val x = NetworkClient.addProperty(
            address = property.propertyaddress,
            city = property.propertycity,
            country = property.propertycountry,
            latitude = "??",
            longitude = "??",
            mortage_info = property.propertymortageinfo,
            pro_status = property.propertystatus,
            purchase_price = property.propertypurchaseprice,
            state = property.propertystate,
            userid = user.id,
            userType = user.usertype
        )
        x.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).logSubscribe("eee")
        x.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).log("kkk").map {
            logz("Mapping..")
            val responseString = it.string()
            if ("Unsuccessful" in responseString) {
                AddPropertyResult.Failure
            } else {
                AddPropertyResult.Success
            }
        }.subscribe(streamAddProperty)
    }

    // Database
    fun whipeDBAndAddUser(user: User) {
        dao.clear()
        dao.addUser(user)
    }

    fun getFirstUserInDB(): User? {
        val users = dao.getUsers()
        if (users.isEmpty()) {
            return null
        } else {
            return users[0]
        }
    }
}