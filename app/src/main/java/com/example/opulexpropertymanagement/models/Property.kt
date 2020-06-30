package com.example.opulexpropertymanagement.models

import android.net.Uri
import android.os.Parcelable
import com.example.opulexpropertymanagement.FBKEY_PROPERTY
import com.example.opulexpropertymanagement.app.fbUserStorageTable
import com.example.opulexpropertymanagement.util.Display
import com.google.firebase.storage.UploadTask
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Property(
    val id: String ="",
    @SerializedName("propertyaddress")
    val streetAddress: String ="",
    @SerializedName("propertycity")
    val city: String ="",
    @SerializedName("propertycountry")
    val country: String ="",
    @SerializedName("propertymortageinfo")
    val mortgageInfo: String ="",
    @SerializedName("propertypurchaseprice")
    val purchasePrice: String ="",
    @SerializedName("propertystate")
    val state: String ="",
    @SerializedName("propertystatus")
    val statusID: String = ""
) : Parcelable {
    val displayablePrice
        get() = Display.asMoney(purchasePrice)
    val singleLineAddress
        get() = "$streetAddress, $city, $state"
    val imageUrlTask
        get() = fbUserStorageTable?.child(FBKEY_PROPERTY)?.child(id)?.downloadUrl
    val status
        get() = PropertyStatusHelper.fromIDToName(statusID)
    fun setImage(uri: Uri): UploadTask? {
        return fbUserStorageTable?.child(FBKEY_PROPERTY)?.child(id)?.putFile(uri)
    }
}