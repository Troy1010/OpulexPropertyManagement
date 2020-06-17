package com.example.opulexpropertymanagement.models.network_responses

import com.example.opulexpropertymanagement.models.Property

data class PropertiesResponse(
    val Property: List<Property>
)