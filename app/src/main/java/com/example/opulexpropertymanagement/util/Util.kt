package com.example.opulexpropertymanagement.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

fun <T> convertRXToLiveData (observable: Observable<T>): LiveData<TryLoginResult> {
    return LiveDataReactiveStreams.fromPublisher(Repo.streamTryLogin.toFlowable(BackpressureStrategy.DROP))
}

fun <T> PublishSubject<T>.toLiveData(): LiveData<TryLoginResult> {
    return convertRXToLiveData(this)
}