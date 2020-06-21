package com.example.opulexpropertymanagement.models



// The NetworkAPI for status does not store more than 10 characters
// So we'll have to store them as digits and restore their name later
enum class PropertyStatus(val id:String) {
    Unavailable("0"), Occupied("1"), Available("2")
}

object PropertyStatusHelper {
    val mapIDToEnum = PropertyStatus.values().associateBy(PropertyStatus::id)
    val mapNameToEnum = PropertyStatus.values().associateBy(PropertyStatus::name)
    fun fromIDToName(id:String): String {
        return mapIDToEnum[id]?.name ?: PropertyStatus.Unavailable.name
    }
    fun fromNameToID(name:String): String {
        return mapNameToEnum[name]?.id ?: PropertyStatus.Unavailable.id
    }
}