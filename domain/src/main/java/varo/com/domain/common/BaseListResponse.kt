package varo.com.domain.common

import com.google.gson.annotations.SerializedName

open class BaseListResponse(
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)