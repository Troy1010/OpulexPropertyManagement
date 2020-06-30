package com.example.opulexpropertymanagement.di

import com.example.opulexpropertymanagement.aa.repo.network.INetworkClient
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.network_responses.ForgotPasswordResponse
import com.example.opulexpropertymanagement.models.network_responses.PropertiesResponse
import com.example.opulexpropertymanagement.models.network_responses.TenantsResponse
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: INetworkClient) : ApiHelper {
    override fun register(
        email: String,
        landlord_email: String,
        password: String,
        userType: String
    ) = apiService.register(email, landlord_email, password, userType)

    override fun tryLogin(email: String, password: String) =
        apiService.tryLogin(email, password)

    override fun forgotPassword(email: String) =
        apiService.forgotPassword(email)

    override fun addProperty(
        address: String,
        city: String,
        state: String,
        country: String,
        pro_status: String,
        purchase_price: String,
        mortage_info: String,
        userid: String,
        userType: String,
        latitude: String,
        longitude: String
    )=
        apiService.addProperty(address, city, state, country, pro_status, purchase_price, mortage_info, userid, userType, latitude, longitude)

    override fun getPropertiesForLandlord(
        usertype: String,
        userid: String
    ) =
        apiService.getPropertiesForLandlord(usertype, userid)

    override fun removeProperty(propertyid: String)=
        apiService.removeProperty(propertyid)

    override fun addTenants(
        name: String,
        email: String,
        address: String,
        mobile: String,
        propertyid: String,
        landlordid: String
    )=
        apiService.addTenants(name, email, address, mobile, propertyid, landlordid)

    override fun getTenantsByLandlord(landlordid: String) =
        apiService.getTenantsByLandlord(landlordid)

    override fun getAllProperties() =
        apiService.getAllProperties()

}