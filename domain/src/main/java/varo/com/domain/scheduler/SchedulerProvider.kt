package varo.com.domain.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProvider @Inject constructor() {

    fun computation() = Schedulers.computation()

    fun io() = Schedulers.io()

    fun mainThread() = AndroidSchedulers.mainThread()

    fun newThread() = Schedulers.newThread()
}