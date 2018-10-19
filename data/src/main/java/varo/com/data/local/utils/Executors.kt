package varo.com.data.local.utils

import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(F: () -> Unit) {
    IO_EXECUTOR.execute(F)
}