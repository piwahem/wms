package com.example.feature.material.data.local

import com.example.base.Error
import com.example.base.Result
import com.example.feature.material.domain.MaterialReceiptScanInformationEntity

interface MaterialLocalContract {
    suspend fun addReceiptInformation(
        userId: String,
        warehouseId: String,
        info: MaterialReceiptScanInformationEntity
    ): Result<MaterialReceiptScanInformationEntity, Error>

    suspend fun getMaterials(
    ): Result<List<MaterialReceiptScanInformationEntity>, Error>

    suspend fun getReceiptDetail(receiptNoNumber: String): Result<MaterialReceiptScanInformationEntity, Error>
}