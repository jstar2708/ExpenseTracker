package com.jaideep.expensetracker.model

import kotlinx.coroutines.Job

class RunJobForData<T> {
    var job: Job? = null
    var data: T? = null

    constructor() {

    }
    constructor(job: Job, data: T) {
        this.job = job
        this.data = data
    }

}