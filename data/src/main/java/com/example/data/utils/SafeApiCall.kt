package com.example.data.utils

import retrofit2.Response

suspend fun <T : Any> safeApiCall(
    call: suspend () -> Response<T>
): Result<T>? {
    return try {
        val response = call.invoke()
        val responseBody = response.body()

        if (response.isSuccessful) {
            Result.Success(responseBody)
        } else {
            Result.Error(Exception("Unknown error"))
        }

    } catch (e: Exception) {
        Result.Error(e)
    }
}