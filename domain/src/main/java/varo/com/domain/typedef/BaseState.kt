package varo.com.domain.typedef

import android.support.annotation.IntDef
import kotlin.annotation.AnnotationRetention.SOURCE

open class BaseState {

    @IntDef(INIT,
        LOADING,
        READY,
        ERROR)
    @Retention(SOURCE)
    annotation class BaseState

    companion object {
        const val INIT = 0
        const val LOADING = 1
        const val READY = 2
        const val ERROR = 3
        const val FETCH = 4
    }
}
