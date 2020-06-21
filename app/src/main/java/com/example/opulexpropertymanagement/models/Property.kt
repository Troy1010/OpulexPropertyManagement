package com.example.opulexpropertymanagement.models

import android.os.Parcelable
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
    val status: String = ""
) : Parcelable {
    val singleLineAddress
        get() = "$streetAddress, $city, $state"
}