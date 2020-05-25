package com.example.feature.login.data

import com.example.base.Error
import com.example.base.Result
import com.example.feature.login.domain.AuthenticationEntity

interface LoginRemoteContract {
    suspend fun login(user: AuthenticationEntity): Result<AuthenticationEntity, Error>
    fun logout(): Result<Boolean, Error>
}