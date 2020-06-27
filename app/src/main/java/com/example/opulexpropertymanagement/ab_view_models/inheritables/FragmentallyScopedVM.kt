package com.example.opulexpropertymanagement.ab_view_models.inheritables

import androidx.navigation.NavController

abstract class FragmentallyScopedVM(
    navController: NavController
) {
    abstract val fragmentsToScopeWith: HashSet<Int>
}