package com.dailyroutineapp.core.models

sealed class Resource<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val message: String = ""
) {
    class Loading<T>(
        isLoading: Boolean
    ): Resource<T>(isLoading)

    class Success<T>(
        isLoading: Boolean = false,
        data: T? = null
    ): Resource<T>(isLoading = isLoading, data = data)

    class Error<T>(
        isLoading: Boolean = false,
        data: T? = null,
        message: String
    ): Resource<T>(isLoading = isLoading, data = data, message = message)
}
