package varo.com.domain.typedef

import android.support.annotation.IntDef
import kotlin.annotation.AnnotationRetention.SOURCE

open class MovieSortOptions {

    @IntDef(NAME,
        RATING)
    @Retention(SOURCE)
    annotation class MovieSortOptions

    companion object {
        const val NAME = 0
        const val RATING = 1
    }
}
