package com.hrishi.minichallenges_pl.core.networking.model

sealed class Result<out T, out E> {
    data class Success<out T>(val data: T) : Result<T, Nothing>()
    data class Error<out E>(val error: E) : Result<Nothing, E>()
}

sealed class NetworkError {
    object NoInternet : NetworkError()
    object Unauthorized : NetworkError()
    object RequestTimeout : NetworkError()
    object ServerError : NetworkError()
    object Unknown : NetworkError()
    object Serialization : NetworkError()
}