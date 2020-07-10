package com.example.opulexpropertymanagement.layers.data_layer

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.layers.data_layer.network.apiClient
import com.example.opulexpropertymanagement.layers.z_ui.User
import com.example.opulexpropertymanagement.FBKEY_PROPERTY
import com.example.opulexpropertymanagement.fbUserStorageTable
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.opulexpropertymanagement.models.streamable.GetPropertiesResult
import com.example.opulexpropertymanagement.models.streamable.RemovePropertyResult
import com.example.tmcommonkotlin.Coroutines
import com.example.tmcommonkotlin.logz

// both FragProperties and FragPropertyAdd need access
object PropertiesRepo {
    //  requestProperties
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
                val x = apiClient.getPropertiesForLandlord(user.usertype, user.id).await()
                GetPropertiesResult.Success(x.Properties)
            } catch (e: Exception) {
                logz("WARNING:Could not get properties")
                GetPropertiesResult.Failure.Unknown
            }
        }, {
            streamRequestPropertiesResult.value = it
        })
    }

    // addProperty
    val streamAddPropertyResult by lazy { MutableLiveData<AddPropertyResult>() }
    fun addProperty(property: Property, user: User, propertyImageUri: Uri) {
        Coroutines.ioThenMain(
            {
                val responseString =  apiClient.addProperty(
                    address = property.streetAddress,
                    city = property.city,
                    country = property.country,
                    latitude = "??",
                    longitude = "??",
                    mortage_info = property.mortgageInfo,
                    pro_status = property.statusID,
                    purchase_price = property.purchasePrice,
                    state = property.state,
                    userid = user.id,
                    userType = user.usertype
                ).await().string()
                if ("successfully added" in responseString) {
                    // then, get our product id (because the api doesn't return it for us, unfortunately)
                    val findPropertiesResult = apiClient.getPropertiesForLandlord(user.usertype, user.id).await()
                    val propertyJustAdded = findPropertiesResult.Properties.findLast { it.singleLineAddress == property.singleLineAddress }
                    if (propertyJustAdded==null) {
                        for (property in findPropertiesResult.Properties) {
                            logz("  property:${property.singleLineAddress}")
                        }
                        logz("NO MATCHES WITH:${property.singleLineAddress}")
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
            }, { result ->
                // finally, upload the image to firebase, then publish to streamAddPropertyResult
                if (result is AddPropertyResult.Success) {
                    result.property.setImage(propertyImageUri)
                        ?.addOnSuccessListener {
                            streamAddPropertyResult.value = result
                        }
                        ?.addOnFailureListener {
                            streamAddPropertyResult.value = AddPropertyResult.Failure.FailedUploadingImageToFirebase
                        }
                } else {
                    streamAddPropertyResult.value = result
                }
            }
        )
    }

    // removeProperty
    val streamRemovePropertyResult by lazy { MutableLiveData<RemovePropertyResult>() }
    fun removeProperty(propertyID: String) {
        Coroutines.ioThenMain(
            {
                val responseBody = apiClient.removeProperty(propertyID).await()
                if ("succesfully" in responseBody.string()) {
                    RemovePropertyResult.Success(propertyID)
                } else {
                    RemovePropertyResult.Failure.Unknown
                }
            },{ result ->
                // finally, remove the image from firebase, then publish delete success
                if (result is RemovePropertyResult.Success) {
                    fbUserStorageTable?.child(FBKEY_PROPERTY)?.child(result.propertyID)?.delete()
                        ?.addOnSuccessListener {
                            logz("Successfully deleted image from firebase. result.propertyID:${result.propertyID}")
                            streamRemovePropertyResult.value = result
                        }
                        ?.addOnFailureListener {
                            logz("Failed to delete image from firebase. result.propertyID:${result.propertyID}")
                            streamRemovePropertyResult.value = result
                        }
                }
            }
        )
    }
}