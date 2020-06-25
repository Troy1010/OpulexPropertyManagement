package com.example.opulexpropertymanagement.models

enum class UserType(val toNetworkRecognizedString:String) {
    Tenant("tenant"), Landlord("landlord")
}
val mapNetworkRecognizedStringToUserType = UserType.values().associate {
    it.toNetworkRecognizedString to it
}