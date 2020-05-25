package com.example.feature.userinformation.domain

import com.example.base.BaseUseCase
import com.example.base.Error
import com.example.base.Result

open class GetUserInformation(
    private val repository: UserContract
) : BaseUseCase<UserInformationEntity>() {
    override suspend fun create(data: Map<String, Any>?): Result<UserInformationEntity, Error> {
        return repository.getUserInformation()
    }

    suspend fun getUserInformation(): Result<UserInformationEntity, Error> {
        val data = HashMap<String, Any>()
        return create(data)
    }
}