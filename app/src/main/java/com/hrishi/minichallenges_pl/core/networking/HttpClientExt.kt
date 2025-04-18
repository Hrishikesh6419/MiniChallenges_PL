package com.hrishi.minichallenges_pl.core.networking

import android.util.Log
import com.hrishi.minichallenges_pl.core.networking.model.NetworkError
import com.hrishi.minichallenges_pl.core.networking.model.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T : Any> HttpClient.getRequest(
    url: String
): Result<T, NetworkError> {
    return safeCall {
        get(url)
    }
}

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        Log.e("ApiClient", "No internet connection", e)
        return Result.Error(NetworkError.NoInternet)
    } catch (e: SerializationException) {
        Log.e("ApiClient", "Serialization error", e)
        return Result.Error(NetworkError.Serialization)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Log.e("ApiClient", "Unknown error", e)
        return Result.Error(NetworkError.Unknown)
    }

    return when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>())
        401 -> Result.Error(NetworkError.Unauthorized)
        408 -> Result.Error(NetworkError.RequestTimeout)
        in 500..599 -> Result.Error(NetworkError.ServerError)
        else -> Result.Error(NetworkError.Unknown)
    }
}