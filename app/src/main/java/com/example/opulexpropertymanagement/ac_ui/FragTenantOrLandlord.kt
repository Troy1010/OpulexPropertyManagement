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

class FragTenantOrLandlord : OXFragment(isDrawerEnabled = false, isToolbarEnabled = false) {
    val navController by lazy { this.findNavController() }
    val globalVM: GlobalVM by activityViewModels()
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
            globalVM.userType.value = UserType.Tenant
        }
        cardview_landlord.setOnClickListener {
            globalVM.userType.value = UserType.Landlord
            val directions = FragTenantOrLandlordDirections.actionGlobalFragProperties()
            navController.navigate(directions)
        }
    }
}