package com.example.feature.userinformation.data

import com.example.base.Error
import com.example.base.Result
import com.example.feature.userinformation.domain.UserInformationEntity

interface UserInformationRemoteContract {
    suspend fun getUserInformation(): Result<UserInformationEntity, Error>
}