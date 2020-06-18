package com.example.opulexpropertymanagement.ab_view_models.inheritables

import kotlinx.coroutines.Job

interface IJobStopper {
    val jobs: ArrayList<Job>
}