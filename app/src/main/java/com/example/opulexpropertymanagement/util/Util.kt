package com.example.opulexpropertymanagement.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.models.streamable.LoginAttempt
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

fun <T> convertRXToLiveData (observable: Observable<T>): LiveData<LoginAttempt> {
    return LiveDataReactiveStreams.fromPublisher(Repo.streamTryLogin.toFlowable(BackpressureStrategy.DROP))
}