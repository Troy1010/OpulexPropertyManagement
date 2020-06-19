package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.ab_view_models.inheritables.IJobStopper
import com.example.opulexpropertymanagement.ab_view_models.inheritables.JobStopper
import com.example.opulexpropertymanagement.models.streamable.LoginAttempt
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.tmcommonkotlin.Coroutines

class LoginVM: ViewModel(), IJobStopper by JobStopper() {
    val loginAttempt by lazy { MutableLiveData<LoginAttempt>() }
    fun tryLogin(email: String, password: String) {
        jobs.add(
            Coroutines.ioThenMain(
                { Repo.tryLogin(email, password)},
                {loginAttempt.value = it}
            )
        )
    }
}