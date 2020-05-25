package com.example.feature.login.data

import android.content.SharedPreferences
import com.example.base.Error
import com.example.base.Result
import com.example.feature.login.domain.AuthenticationEntity
import com.google.gson.Gson

class LoginLocalDataSource(
    private val preferences: SharedPreferences,
    private val mapper: LoginMapper
) : LoginLocalContract {

    override fun saveCredential(user: AuthenticationEntity): Result<Boolean, Error> {
        preferences.edit().putString(KEY_USER_CREDENTIALS, Gson().toJson(mapper.mapEntityToLocal(user))).commit()
        return Result.Success(true)
    }

    override fun getCredential(): Result<AuthenticationEntity, Error> {
        val value = preferences.getString(KEY_USER_CREDENTIALS, "")
        return if (!value.isNullOrEmpty()) {
            val data = Gson().fromJson(value, AuthenticationDTO::class.java)
            Result.Success(mapper.mapLocalToEntity(data))
        } else {
            Result.Failure(Error.RandomError(Throwable("Do not have credential")))
        }
    }

    override fun clearCredential(): Result<Boolean, Error> {
        preferences.edit().remove(KEY_USER_CREDENTIALS).commit()
        return Result.Success(true)
    }

    companion object {
        const val KEY_USER_CREDENTIALS = "USER_DATA"
    }
}