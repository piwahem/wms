package com.example.base

abstract class BaseUseCase<T>() {
    abstract suspend fun create(data: Map<String, Any>? = null): Result<T, Error>
}