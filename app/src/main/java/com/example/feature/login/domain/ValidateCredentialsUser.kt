package com.example.feature.login.domain

import com.example.base.BaseUseCase
import com.example.base.Error
import com.example.base.Result

open class ValidateCredentialsUser(
    private val repository: LoginContract
) : BaseUseCase<Boolean>() {
    override suspend fun create(data: Map<String, Any>?): Result<Boolean, Error> {
        val username = (data?.get(KEY_USER_NAME) as? String).orEmpty()
        return repository.validate(AuthenticationEntity(username))
    }

    suspend fun validate(
        username: String
    ): Result<Boolean, Error> {
        val data = HashMap<String, Any>()
        data[KEY_USER_NAME] = username
        return create(data)
    }

    companion object {
        const val KEY_USER_NAME = "KEY_USER_NAME"
    }
}