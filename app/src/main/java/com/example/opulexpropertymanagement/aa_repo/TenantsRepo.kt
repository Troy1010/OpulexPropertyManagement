package com.example.opulexpropertymanagement.aa_repo

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.aa_repo.network.NetworkClient
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.app.FBKEY_TENANT
import com.example.opulexpropertymanagement.app.fbUserStorageTable
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.models.streamable.AddTenantResult
import com.example.opulexpropertymanagement.models.streamable.RemoveTenantResult
import com.example.tmcommonkotlin.Coroutines
import com.example.tmcommonkotlin.logz

object TenantsRepo {
    // addTenant
    val streamAddTenantResult by lazy { MutableLiveData<AddTenantResult>() }
    fun addTenant(tenant: Tenant, tenantImageUri: Uri) {
        Coroutines.ioThenMain(
            {
                val responseString =  NetworkClient.addTenants(
                    address = tenant.fullAddress,
                    email = tenant.email,
                    landlordid = tenant.landlordID,
                    mobile = tenant.phone,
                    name = tenant.name,
                    propertyid = tenant.propertyid
                ).await().string()
                if ("successfully added" in responseString) {
                    // then, get our tenant id (because the api doesn't return it for us, unfortunately)
                    val tenantsResponse = NetworkClient.getTenantsByLandlord(tenant.landlordID).await()
                    val tenantJustAdded = tenantsResponse.Tenants.findLast {
                        (it.name == tenant.name) && (it.propertyid == tenant.propertyid) && (it.email == tenant.email)
                    }
                    if (tenantJustAdded==null) {
                        for (tenant in tenantsResponse.Tenants) {
                            logz("  tenant:$tenant")
                        }
                        logz("NO MATCHES WITH:$tenant")
                        AddTenantResult.Failure.CouldNotFindTenantJustAdded
                    } else {
                        AddTenantResult.Success(tenantJustAdded)
                    }
                } else if ("Email already exist" in responseString) { // Sometimes the api responds with a string..
                    AddTenantResult.Failure.EmailAlreadyExists
                } else if ("property not found" in responseString) { // and sometimes the api responds with an object..
                    AddTenantResult.Failure.PropertyNotFound
                } else if ("wrong landlord id" in responseString) {
                    AddTenantResult.Failure.WrongLandlordID
                } else {
                    AddTenantResult.Failure.Unknown
                }
            }, { result ->
                // finally, upload the image to firebase, then publish to streamAddPropertyResult
                if (result is AddTenantResult.Success) {
                    result.tenant.setImage(tenantImageUri)
                        ?.addOnSuccessListener {
                            streamAddTenantResult.value = result
                        }
                        ?.addOnFailureListener {
                            streamAddTenantResult.value = AddTenantResult.Failure.FailedToUploadImage
                        }
                } else {
                    streamAddTenantResult.value = result
                }
            }
        )
    }

    // removeTenant
    val streamRemoveTenantResult by lazy { MutableLiveData<RemoveTenantResult>() }
    fun removeTenant(tenantID: String) {
        // THE NETWORK API CURRENTLY DOES NOT SUPPORT REMOVING TENANTS
        logz("WARNING: The network api currently does not support removing tenants")
        streamRemoveTenantResult.value = RemoveTenantResult.Failure.ApiDoesNotSupportRemovingTenants
    }
}