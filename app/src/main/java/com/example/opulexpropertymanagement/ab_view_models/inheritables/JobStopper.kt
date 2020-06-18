package com.example.opulexpropertymanagement.ab_view_models.inheritables

import kotlinx.coroutines.Job

class JobStopper: IJobStopper {
    override val jobs = ArrayList<Job>()
    fun finalize() {
        for (job in jobs) {
            job.cancel()
        }
    }
}