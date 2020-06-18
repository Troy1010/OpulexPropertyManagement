package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.ab_view_models.inheritables.IJobStopper
import com.example.opulexpropertymanagement.ab_view_models.inheritables.JobStopper
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.LoginAttempt
import com.example.tmcommonkotlin.Coroutines

class RegisterVM: ViewModel(), IJobStopper by JobStopper() {
    val registrationAttempt by lazy { MutableLiveData<LoginAttempt>() }
    fun register(email: String, password: String, userType: UserType) {
        jobs.add(
            Coroutines.ioThenMain(
                {Repo.register(email, password, userType)},
                {registrationAttempt.value = it}
            )
        )
    }
}