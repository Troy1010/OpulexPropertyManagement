package com.example.opulexpropertymanagement.aa_repo

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.app.fbUserStorageTable
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.opulexpropertymanagement.models.streamable.GetPropertiesResult
import com.example.tmcommonkotlin.Coroutines
import com.example.tmcommonkotlin.logz

// both FragProperties and FragPropertyAdd need access
object PropertiesRepo {
    //  Properties
    val streamRequestPropertiesResult by lazy { MutableLiveData<GetPropertiesResult>() }

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

    val streamAddPropertyResult by lazy { MutableLiveData<AddPropertyResult>() }
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
                    val findPropertiesResult = NetworkClient.getPropertiesForLandlord(user.usertype, user.id).await()
                    val propertyJustAdded = findPropertiesResult.Properties.find { it.singleLineAddress == property.singleLineAddress }
                    if (propertyJustAdded==null) {
                        AddPropertyResult.Failure.DidNotReceiveProjectID
                    } else {
                        AddPropertyResult.Success(user, propertyJustAdded)
                    }
                } else if ("mismatch user id or user type" in responseString) {
                    AddPropertyResult.Failure.MismatchedUserIDVsType
                } else if ("user type should landlord" in responseString) {
                    AddPropertyResult.Failure.UserTypeShouldBeLandlord
                } else {
                    AddPropertyResult.Failure.Unknown
                }
            }, {
                streamAddPropertyResult.value = it
                // update firebase with image
                if (it is AddPropertyResult.Success) {
                    fbUserStorageTable?.child(it.property.id)?.putFile(propertyImageUri)
                        ?.addOnSuccessListener {
                            logz("Success!")
                        }
                        ?.addOnFailureListener {
                            logz("FAILED:$it")
                        }
                }
            }
        )
    }
}