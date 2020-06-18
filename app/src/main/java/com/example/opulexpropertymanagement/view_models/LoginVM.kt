package com.example.opulexpropertymanagement.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttempt
import com.example.opulexpropertymanagement.ui.Repo
import com.example.tmcommonkotlin.Coroutines
import kotlinx.coroutines.Job

class LoginVM: ViewModel() {

    val loginAttempt by lazy { MutableLiveData<StreamableLoginAttempt>() }

    fun tryLogin(email: String, password: String) {
        jobs.add(
            Coroutines.ioThenMain(
                { Repo.tryLogin(email, password)},
                {loginAttempt.value = it}
            )
        )
    }

    val jobs = ArrayList<Job>()
    fun finalize() {
        for (job in jobs) {
            job.cancel()
        }
    }
}