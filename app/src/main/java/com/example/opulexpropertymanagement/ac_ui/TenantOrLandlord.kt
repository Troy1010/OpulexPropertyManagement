package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.ab_view_models.GlobalVM
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_tenant_or_landlord.*

class TenantOrLandlord : OXFragment(isDrawerEnabled = false, isToolbarEnabled = false) {
    val navController by lazy { this.findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.frag_tenant_or_landlord,container,false)
        return v
    }

    override fun onStart() {
        super.onStart()
        cardview_tenant.setOnClickListener {
            logz("tenant")
            GlobalVM.userType.value = UserType.Tenant
        }
        cardview_landlord.setOnClickListener {
            GlobalVM.userType.value = UserType.Landlord
            navController.navigate(R.id.action_global_fragProperties)
        }
    }
}