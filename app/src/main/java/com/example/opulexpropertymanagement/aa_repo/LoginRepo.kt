package com.example.opulexpropertymanagement.aa_repo

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.ac_ui.GlobalRepo
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.tmcommonkotlin.logz

class LoginRepo {
    val streamLoginAttemptOverloadTimestamp = MediatorLiveData<Long>()
    init {
        streamLoginAttemptOverloadTimestamp.addSource(GlobalRepo.streamLoginAttemptResult) {
            if (it is TryLoginResult.Failure.TooManyAttempts) {
                streamLoginAttemptOverloadTimestamp.value = System.currentTimeMillis()/1000
            }
        }
    }
}