package com.example.feature.login.domain

import com.example.base.BaseUseCase
import com.example.base.Error
import com.example.base.Result

open class GetLoginInformation(
    private val repository: LoginContract
) : BaseUseCase<AuthenticationEntity>() {
    override suspend fun create(data: Map<String, Any>?): Result<AuthenticationEntity, Error> {
        return repository.getLoginInfo()
    }

    suspend fun getLoginInformation(): Result<AuthenticationEntity, Error> {
        val data = HashMap<String, Any>()
        return create(data)
    }
}