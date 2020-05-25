package com.example.feature.login.domain

import com.example.base.Error
import com.example.base.Result

interface LoginContract {
    suspend fun signIn(entity: AuthenticationEntity): Result<AuthenticationEntity, Error>
    fun validate(entity: AuthenticationEntity): Result<Boolean, Error>
    fun isUserLogin(): Result<Boolean, Error>
    fun getLoginInfo(): Result<AuthenticationEntity, Error>
    fun signOut(): Result<Boolean, Error>
}