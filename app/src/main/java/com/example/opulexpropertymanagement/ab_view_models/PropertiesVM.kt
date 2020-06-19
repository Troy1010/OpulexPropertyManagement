package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.ab_view_models.inheritables.IJobStopper
import com.example.opulexpropertymanagement.ab_view_models.inheritables.JobStopper
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.models.Property
import com.example.tmcommonkotlin.Coroutines

class PropertiesVM: ViewModel(), IJobStopper by JobStopper() {
    val properties by lazy { MutableLiveData<ArrayList<Property>>().apply { value = ArrayList() } }
    fun addProperty(property: Property, user: User) {
        Repo.addProperty(property, user)
//        jobs.add(
//            Coroutines.ioThenMain(
//                {Repo.addProperty(property, user)},
//                {loginAttempt.value = it}
//            )
//        )
    }
}