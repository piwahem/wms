package com.example.feature.material.data.remote

import com.example.base.Error
import com.example.base.Result
import com.example.feature.material.domain.MaterialReceiptScanInformationEntity

interface MaterialRemoteContract {
    suspend fun registerReceiptInformation(
        userId: String,
        warehouseId: String,
        info: MaterialReceiptScanInformationEntity
    ): Result<MaterialReceiptScanInformationEntity, Error>
}