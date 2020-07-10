package com.example.opulexpropertymanagement.layers.view_models

import androidx.lifecycle.*
import com.example.opulexpropertymanagement.layers.data_layer.SharedPrefWrapper
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.layers.z_ui.GlobalRepo
import com.example.opulexpropertymanagement.layers.z_ui.User
import com.example.opulexpropertymanagement.models.mapNetworkRecognizedStringToUserType

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
        user.value = SharedPrefWrapper.getUserFromSharedPref()
        user.addSource(repo.streamLoginAttemptResult) {
            user.value = it.user
            user.value?.usertype = userType.value?.toNetworkRecognizedString ?:""
        }
    }

    fun logout() {
        user.value = null
    }

}