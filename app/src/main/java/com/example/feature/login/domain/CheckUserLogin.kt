package com.example.feature.login.domain

import com.example.base.BaseUseCase
import com.example.base.Error
import com.example.base.Result

open class CheckUserLogin(
    private val repository: LoginContract
) : BaseUseCase<Boolean>() {
    override suspend fun create(data: Map<String, Any>?): Result<Boolean, Error> {
        return repository.isUserLogin()
    }

    suspend fun isUserLogin(
    ): Result<Boolean, Error> {
        return create(null)
    }
}