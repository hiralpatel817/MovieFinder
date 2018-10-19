package varo.com.moviefinder.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    var disposibles: CompositeDisposable? = null

    init {
        disposibles = CompositeDisposable()
    }

    internal fun addDisposible(disposable: Disposable) {
        disposibles?.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposibles?.dispose()
    }
}