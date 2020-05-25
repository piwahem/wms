package com.example.feature.material.domain

import com.example.base.Result
import com.example.base.Error

interface MaterialContract {
    suspend fun submitReceiptInformation(
        userId: String,
        warehouseId: String,
        info: MaterialReceiptScanInformationEntity
    ): Result<MaterialReceiptScanInformationEntity, Error>

    suspend fun generateReceiptNoNumber(): Result<String, Error>
    suspend fun getReceiptDetail(receiptNoNumber: String): Result<MaterialReceiptScanInformationEntity, Error>
}