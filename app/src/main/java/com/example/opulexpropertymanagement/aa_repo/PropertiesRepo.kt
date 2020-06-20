package com.example.opulexpropertymanagement.aa_repo

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.ab_view_models.GlobalVM
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.opulexpropertymanagement.models.streamable.GetPropertiesResult
import com.example.tmcommonkotlin.Coroutines
import com.example.tmcommonkotlin.logz

class PropertiesRepo {
    //  Properties
    val properties by lazy { MediatorLiveData<List<Property>>() }
    val streamRequestPropertiesResult by lazy { MutableLiveData<GetPropertiesResult>() }
    val streamAddPropertyResult by lazy { MutableLiveData<AddPropertyResult>() }
    init {
        properties.addSource(streamRequestPropertiesResult) {
            if (it is GetPropertiesResult.Success) {
                properties.value = it.properties
            }
        }
        properties.addSource(streamAddPropertyResult) {
            if (it is AddPropertyResult.Success) {
                requestPropertiesByUser(it.user)
            }
        }
        val user = GlobalVM.user.value
        if (user != null) {
            requestPropertiesByUser(user)
        }
    }

    fun requestPropertiesByUser(user: User) {
        Coroutines.ioThenMain({
            if (user.usertype != UserType.Landlord.toNetworkRecognizedString) {
                logz("WARNING:Attempted to getPropertiesByUser for a user that is not a landlord")
                GetPropertiesResult.Failure.UserNotALandlord
            } else if (user.id == "") {
                logz("WARNING:Attempted to getPropertiesByUser for a user without a userID")
                GetPropertiesResult.Failure.InvalidUserID
            }
            try {
                val x = NetworkClient.getPropertiesForLandlord(user.usertype, user.id).await()
                GetPropertiesResult.Success(x.Properties)
            } catch (e: Exception) {
                logz("WARNING:Could not get properties")
                GetPropertiesResult.Failure.Unknown
            }
        }, {
            streamRequestPropertiesResult.value = it
        })
    }

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
                    AddPropertyResult.Success(user)
                } else if ("mismatch user id or user type" in responseString) {
                    AddPropertyResult.Failure.MismatchedUserIDVsType
                } else if ("user type should landlord" in responseString) {
                    AddPropertyResult.Failure.UserTypeShouldBeLandlord
                } else {
                    AddPropertyResult.Failure.Unknown
                }
            }, {
                streamAddPropertyResult.value = it
            }
        )
    }
}