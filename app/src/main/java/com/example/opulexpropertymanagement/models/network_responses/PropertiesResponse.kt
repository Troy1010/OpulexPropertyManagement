package com.example.opulexpropertymanagement.models.network_responses

import com.example.opulexpropertymanagement.models.Property
import com.google.gson.annotations.SerializedName

data class PropertiesResponse(
    @SerializedName("Property")
    val Properties: List<Property>
)