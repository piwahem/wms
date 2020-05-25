package com.example.feature.userinformation.data

import com.example.base.Error
import com.example.base.Result
import com.example.extension.executeRequest
import com.example.feature.core.network.ApiInterface
import com.example.feature.userinformation.domain.UserInformationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserInformationRemoteDataSource(
    private val api: ApiInterface,
    private val mapper: UserInformationMapper
) : UserInformationRemoteContract {

    override suspend fun getUserInformation(): Result<UserInformationEntity, Error> {
        return withContext(Dispatchers.IO) {
            return@withContext when (val result = executeRequest { api.getUserInformation() }) {
                is Result.Success -> {
                    Result.Success(mapper.mapNetworkToEntity(result.successData))
                }
                is Result.Failure -> {
                    result
                }
            }
        }
    }
}