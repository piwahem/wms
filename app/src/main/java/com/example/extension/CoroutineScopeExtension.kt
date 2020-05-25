package com.example.extension

import retrofit2.Response
import kotlinx.coroutines.CoroutineScope
import java.io.IOException
import com.example.base.Error
import com.example.base.Result

inline fun <T> CoroutineScope.executeRequest(request: () -> Response<T>): Result<T, Error> {
    return try {
        val response = request.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.Success(it)
            } ?: run {
                Result.Failure(Error.EmptyBodyError())
            }
        } else {
            Result.Failure(Error.RandomError(Throwable(response.errorBody()?.string())))
        }
    } catch (e: Exception) {
        e.printStackTrace()
        when (e) {
            is IOException -> {
                Result.Failure(Error.NetworkError())
            }
            else -> {
                Result.Failure(Error.RandomError(e.cause))
            }
        }
    }
}
