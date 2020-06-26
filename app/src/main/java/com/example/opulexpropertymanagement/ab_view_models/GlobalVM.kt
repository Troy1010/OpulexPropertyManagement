package com.example.opulexpropertymanagement.ab_view_models

import android.view.View
import androidx.lifecycle.*
import com.example.opulexpropertymanagement.aa_repo.network.NetworkClient
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.ac_ui.GlobalRepo
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.models.mapNetworkRecognizedStringToUserType
import com.example.tmcommonkotlin.logz

// this is intended to be an activity-level VM
object GlobalVM : ViewModel() {
    val repo = GlobalRepo

    val user = MediatorLiveData<User?>() // Other fragment-level VMs require this
    val userType by lazy { MediatorLiveData<UserType>() }

    init {
        userType.addSource(user) {
            if (it!=null)
                userType.value = mapNetworkRecognizedStringToUserType[it.usertype]
        }
        user.value = GlobalRepo.sharedPref.getUserFromSharedPref()
        user.addSource(repo.streamLoginAttemptResult) {
            user.value = it.user
            user.value?.usertype = userType.value?.toNetworkRecognizedString ?:""
        }
    }

    fun logout() {
        user.value = null
    }

}