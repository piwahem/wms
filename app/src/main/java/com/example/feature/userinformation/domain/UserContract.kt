package com.example.feature.userinformation.domain

import com.example.base.Error
import com.example.base.Result

interface UserContract {
    suspend fun getUserInformation(): Result<UserInformationEntity, Error>
}