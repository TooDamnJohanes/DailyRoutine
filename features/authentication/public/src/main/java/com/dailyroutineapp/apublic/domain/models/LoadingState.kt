package com.dailyroutineapp.apublic.domain.models

sealed class LoadingState(val status: Status) {
    class Success: LoadingState(Status.SUCCESS)
    class Failed: LoadingState(Status.FAILED)
    class Loading: LoadingState(Status.LOADING)
    class Idle: LoadingState(Status.IDLE)
}

enum class Status {
    SUCCESS,
    FAILED,
    LOADING,
    IDLE
}
