package com.example.opulexpropertymanagement.ab_view_models.inheritables

import androidx.navigation.NavController
import com.example.opulexpropertymanagement.ab_view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.util.getValue
import com.example.opulexpropertymanagement.util.setValue
import com.example.tmcommonkotlin.logz
import kotlin.reflect.KMutableProperty0


abstract class FragmentallyScopedVM(
    navController: NavController,
    instanceReference: KMutableProperty0<FragmentallyScopedVM?>
) {
    var instance by instanceReference
    abstract val fragmentsToScopeWith: HashSet<Int>
    init {
        navController.addOnDestinationChangedListener { navController , destination, bundle ->
            val tenantDetailsVMZ = instance
            if ((tenantDetailsVMZ!=null) && destination.id !in tenantDetailsVMZ.fragmentsToScopeWith) {
                logz("making tenantDetailsVM null")
                instance = null
                System.gc()
            }
        }
    }
}