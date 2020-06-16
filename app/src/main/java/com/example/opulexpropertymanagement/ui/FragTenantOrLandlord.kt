package com.example.opulexpropertymanagement.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.models.UserType
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_tenant_or_landlord.*

class FragTenantOrLandlord : Fragment(R.layout.frag_tenant_or_landlord) {
    val navController by lazy { this.findNavController() }
    val userVM: UserVM by viewModels()
    override fun onStart() {
        super.onStart()
        cardview_tenant.setOnClickListener {
            logz("tenant")
            userVM.userType.value = UserType.Tenant
        }
        cardview_landlord.setOnClickListener {
            userVM.userType.value = UserType.Landlord
            val directions = FragTenantOrLandlordDirections.actionFragTenantOrLandlordToFragHome()
            navController.navigate(directions)
        }
    }
}