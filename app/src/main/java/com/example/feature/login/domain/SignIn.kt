package com.example.feature.login.domain

import com.example.base.BaseUseCase
import com.example.base.Error
import com.example.base.Result

open class SignIn(
    private val repository: LoginContract
) : BaseUseCase<AuthenticationEntity>() {
    override suspend fun create(data: Map<String, Any>?): Result<AuthenticationEntity, Error> {
        val username = (data?.get(KEY_USER_NAME) as? String).orEmpty()
        val password = (data?.get(KEY_PASSWORD) as? String).orEmpty()
        return repository.signIn(AuthenticationEntity(username, password,""))
    }

    suspend fun authenticate(
        username: String,
        password: String
    ): Result<AuthenticationEntity, Error> {
        val data = HashMap<String, Any>()
        data[KEY_USER_NAME] = username
        data[KEY_PASSWORD] = password
        return create(data)
    }

    companion object {
        const val KEY_USER_NAME = "KEY_USER_NAME"
        const val KEY_PASSWORD = "KEY_PASSWORD"
    }
}