package com.example.feature.userinformation.data

import android.content.SharedPreferences
import com.example.base.Error
import com.example.base.Result
import com.example.feature.userinformation.domain.UserInformationEntity
import com.google.gson.Gson

class UserInformationLocalDataSource(
    private val preferences: SharedPreferences,
    private val mapper: UserInformationMapper
) : UserInformationLocalContract {

    override fun saveUserInformation(user: UserInformationEntity): Result<Boolean, Error> {
        preferences.edit().putString(KEY_USER_INFORMATION, Gson().toJson(mapper.mapEntityToLocal(user))).commit()
        return Result.Success(true)
    }

    override fun getUserInformation(): Result<UserInformationEntity, Error> {
        val value = preferences.getString(KEY_USER_INFORMATION, "")
        return if (!value.isNullOrEmpty()) {
            val data = Gson().fromJson(value, UserInformationDTO::class.java)
            Result.Success(mapper.mapLocalToEntity(data))
        } else {
            Result.Failure(Error.RandomError(Throwable("Do not have user information")))
        }
    }

    override fun clearUserInformation(): Result<Boolean, Error> {
        preferences.edit().remove(KEY_USER_INFORMATION).commit()
        return Result.Success(true)
    }

    companion object {
        const val KEY_USER_INFORMATION = "KEY_USER_INFORMATION"
    }
}