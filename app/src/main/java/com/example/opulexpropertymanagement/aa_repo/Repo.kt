package com.example.opulexpropertymanagement.ac_ui

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.opulexpropertymanagement.aa_repo.NetworkClient
import com.example.opulexpropertymanagement.aa_repo.SharedPref
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.opulexpropertymanagement.models.streamable.GetPropertiesResult
import com.example.opulexpropertymanagement.models.streamable.RegisterResult
import com.example.tmcommonkotlin.Coroutines
import com.example.tmcommonkotlin.logz
import com.google.gson.Gson


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
                    logz("SuccessfulLogin`user:$user")
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
        logz("userType.toNetworkRecognizedString:${userType.toNetworkRecognizedString}")
        val resultString = NetworkClient.register(email, email, password, userType.toNetworkRecognizedString)
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

    //  AddProperty
    val liveDataAddProperty by lazy { MutableLiveData<AddPropertyResult>() }
    fun addProperty(property: Property, user: User) {
        logz("adding property using id:${user.id}")
        Coroutines.ioThenMain(
            {
                val responseString =  NetworkClient.addProperty(
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
                ).await().string()
                logz("addProperty`responseString:$responseString")
                if ("successfully added" in responseString) {
                    AddPropertyResult.Success
                } else if ("mismatch user id or user type" in responseString) {
                    AddPropertyResult.Failure.MismatchedUserIDVsType
                } else if ("user type should landlord" in responseString) {
                    AddPropertyResult.Failure.UserTypeShouldBeLandlord
                } else {
                    AddPropertyResult.Failure.Unknown
                }
            }, {
                liveDataAddProperty.value = it
            }
        )
    }

    //  GetProperties
    suspend fun getPropertiesByUser(user: User): GetPropertiesResult {
        if (user.usertype != UserType.Landlord.toNetworkRecognizedString) {
            logz("WARNING:Attempted to getPropertiesByUser for a user that is not a landlord")
            return GetPropertiesResult.Failure.UserNotALandlord
        } else if (user.id == "") {
            logz("WARNING:Attempted to getPropertiesByUser for a user without a userID")
            return GetPropertiesResult.Failure.InvalidUserID
        }
        return try {
            val x = NetworkClient.getPropertiesForLandlord(user.usertype, user.id).await()
            GetPropertiesResult.Success(x.Properties)
        } catch (e: Exception) {
            logz("WARNING:Could not get properties")
            GetPropertiesResult.Failure.Unknown
        }
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