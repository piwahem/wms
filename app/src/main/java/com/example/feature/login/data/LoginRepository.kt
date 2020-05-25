package com.example.feature.login.data

import com.example.base.Error
import com.example.base.Result
import com.example.feature.login.domain.AuthenticationEntity
import com.example.feature.login.domain.LoginContract

class LoginRepository(
    private val localDataSource: LoginLocalContract,
    private val remoteDataSource: LoginRemoteContract
) : LoginContract {

    override suspend fun signIn(entity: AuthenticationEntity): Result<AuthenticationEntity, Error> {
        val result = remoteDataSource.login(entity)
        result?.success()?.let {
            localDataSource.saveCredential(it)
        }
        return result
    }

    override fun validate(entity: AuthenticationEntity): Result<Boolean, Error> {
        return Result.Success(true)
    }

    override fun isUserLogin(): Result<Boolean, Error> {
        val isHasToken = (localDataSource.getCredential()?.success() != null)
        return if (isHasToken) {
            Result.Success(true)
        } else {
            Result.Failure(Error.RandomError(Throwable("User not login yet")))
        }
    }

    override fun signOut(): Result<Boolean, Error> {
        return localDataSource.clearCredential()
    }

    override fun getLoginInfo(): Result<AuthenticationEntity, Error> {
        return localDataSource.getCredential()
    }
}