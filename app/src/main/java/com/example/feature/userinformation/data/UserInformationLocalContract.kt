package com.example.feature.userinformation.data

import com.example.base.Error
import com.example.base.Result
import com.example.feature.userinformation.domain.UserInformationEntity

interface UserInformationLocalContract {
    fun saveUserInformation(user: UserInformationEntity): Result<Boolean, Error>
    fun getUserInformation(): Result<UserInformationEntity, Error>
    fun clearUserInformation(): Result<Boolean, Error>
}