package com.example.feature.login.data

import com.example.base.Error
import com.example.base.Result
import com.example.extension.executeRequest
import com.example.feature.core.network.ApiInterface
import com.example.feature.login.domain.AuthenticationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRemoteDataSource(
    private val api: ApiInterface,
    private val mapper: LoginMapper
): LoginRemoteContract {

    override suspend fun login(user: AuthenticationEntity): Result<AuthenticationEntity, Error> {
        return withContext(Dispatchers.IO) {
            val (userId, password, deviceID) = user
            return@withContext when (val result = executeRequest { api.signIn(userId, password, deviceID) }){
                is Result.Success -> {
                   Result.Success(mapper.mapNetworkToEntity(result.successData))
                }
                is Result.Failure -> {
                    result
                }
            }
        }
    }

    override fun logout(): Result<Boolean, Error> {
        return Result.Success(true)
    }
}