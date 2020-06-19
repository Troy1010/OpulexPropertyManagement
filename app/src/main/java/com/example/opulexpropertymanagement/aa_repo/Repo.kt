package com.example.opulexpropertymanagement.ac_ui

import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.StreamableTryLogin
import com.example.opulexpropertymanagement.aa_repo.NetworkClient
import com.example.opulexpropertymanagement.aa_repo.SharedPref
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.streamable.StreamableAddProperty
import com.example.tmcommonkotlin.logSubscribe
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


object Repo {
    // SharedPref
    val sharedPref = SharedPref
    // Network
    val streamAddProperty by lazy {PublishSubject.create<StreamableAddProperty>()}
    val streamTryLogin by lazy {PublishSubject.create<StreamableTryLogin>()}
//    val streamTryLogin by lazy { Publisher<LoginAttempt>(function = {}) }
    suspend fun register(email: String, password: String, userType: UserType): StreamableTryLogin {
        return StreamableTryLogin.Failure("Registration failed")
//        val result = NetworkClient.register(email, email, password, userType.name)
//            .await()
//        if ("success" in result.string()) {
//            return tryLogin(email, password)
//        } else {
//            return LoginAttempt.Failure("Registration failed")
//        }
    }

    fun tryLogin(email: String, password: String) {
        NetworkClient.tryLogin(email, password)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map {
                val responseString = it.string()
                if ("success" in responseString) {
                    val user = Gson().fromJson(responseString, User::class.java)
                    StreamableTryLogin.Success(user)
                } else {
                    StreamableTryLogin.Failure("Unknown error")
                }
            }.subscribe(streamTryLogin)
    }
    init {
        streamAddProperty.logSubscribe("mmm")
    }

    fun addProperty(property: Property, user: User) {
        NetworkClient.addProperty(
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
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map {
            val responseString = it.string()
            if ("Unsuccessful" in responseString) {
                StreamableAddProperty.Failure
            } else {
                StreamableAddProperty.Success
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