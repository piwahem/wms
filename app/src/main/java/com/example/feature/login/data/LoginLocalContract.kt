package com.example.feature.login.data

import com.example.base.Result
import com.example.feature.login.domain.AuthenticationEntity
import com.example.base.Error

interface LoginLocalContract {
    fun saveCredential(user: AuthenticationEntity): Result<Boolean, Error>
    fun getCredential(): Result<AuthenticationEntity, Error>
    fun clearCredential(): Result<Boolean, Error>
}