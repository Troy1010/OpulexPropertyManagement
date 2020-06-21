package com.example.opulexpropertymanagement.aa_repo

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.app.fbTable
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.opulexpropertymanagement.models.streamable.GetPropertiesResult
import com.example.tmcommonkotlin.Coroutines
import com.example.tmcommonkotlin.logz
import java.io.File

// both FragProperties and FragPropertyAdd need access
object PropertiesRepo {
    //  Properties
    val streamRequestPropertiesResult by lazy { MutableLiveData<GetPropertiesResult>() }
    val streamAddPropertyResult by lazy { MutableLiveData<AddPropertyResult>() }

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

    fun addProperty(property: Property, user: User, propertyImageUri: Uri) {
        logz("adding property using id:${user.id}")
        Coroutines.ioThenMain(
            {
                val responseString =  NetworkClient.addProperty(
                    address = property.streetAddress,
                    city = property.city,
                    country = property.country,
                    latitude = "??",
                    longitude = "??",
                    mortage_info = property.mortgageInfo,
                    pro_status = property.status,
                    purchase_price = property.purchasePrice,
                    state = property.state,
                    userid = user.id,
                    userType = user.usertype
                ).await().string()
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
                // use that new property's id to add image to fb
                if (it is AddPropertyResult.Success) {
//                    val r1: StorageRe = fbTable?.child(property.streetAddress)
//                    val r2 = fbTable?.child("images/${property.streetAddress}")
//
//                    val file = propertyImageUri.getPath()?.let{ File(it) }
//                    r1?.setValue(file)
                }
            }
        )
    }
}