package com.example.opulexpropertymanagement.ab_view_models.inheritables

import androidx.navigation.NavController
import com.example.opulexpropertymanagement.ab_view_models.TenantDetailsVM
import com.example.tmcommonkotlin.logz

var tenantDetailsVM : TenantDetailsVM?=null
fun getTenantVM(navController:NavController): TenantDetailsVM {
    if (tenantDetailsVM==null) {
        tenantDetailsVM = TenantDetailsVM(navController)
    }
    return tenantDetailsVM!!
}
abstract class FragmentallyScopedVM(
    navController: NavController
) {
    abstract val fragmentsToScopeWith: HashSet<Int>
    init {
        navController.addOnDestinationChangedListener { navController , destination, bundle ->
            val tenantDetailsVMZ = tenantDetailsVM
            if ((tenantDetailsVMZ!=null) && destination.id !in tenantDetailsVMZ.fragmentsToScopeWith) {
                logz("making tenantDetailsVM null")
                tenantDetailsVM = null
                System.gc()
            }
        }
    }
}