package varo.com.data.remote.client

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import varo.com.data.remote.config.HeaderConfig

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Chain): Response {
        val requestBuilder = chain.request().newBuilder().apply {
            addHeader(HeaderConfig.KEY_ACCEPT, HeaderConfig.VALUE_APPLICATION_JSON)
            addHeader(HeaderConfig.KEY_CONTENT_TYPE, HeaderConfig.VALUE_APPLICATION_JSON)
        }

        return chain.proceed(requestBuilder.build())
    }
}