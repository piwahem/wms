package com.example.feature.userinformation.data

import com.example.base.Error
import com.example.base.Result
import com.example.feature.userinformation.domain.UserContract
import com.example.feature.userinformation.domain.UserInformationEntity

class UserInformationRepository(
    private var remote: UserInformationRemoteContract,
    private var local: UserInformationLocalDataSource
) : UserContract {
    override suspend fun getUserInformation(): Result<UserInformationEntity, Error> {
        val result = remote.getUserInformation()
        result?.success()?.let {
            local.saveUserInformation(it)
        }
        return result
    }
}