package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.ab_view_models.inheritables.IJobStopper
import com.example.opulexpropertymanagement.ab_view_models.inheritables.JobStopper
import com.example.opulexpropertymanagement.models.streamable.LoginAttempt
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.util.convertRXToLiveData
import com.example.tmcommonkotlin.Coroutines
import io.reactivex.BackpressureStrategy

class LoginVM: ViewModel() {
    val liveDataTryLogin by lazy { convertRXToLiveData(Repo.streamTryLogin) }
    fun tryLogin(email: String, password: String) {
        Repo.tryLogin(email, password)
    }
}