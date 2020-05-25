package com.example.feature.material.data

import com.example.base.Error
import com.example.base.Result
import com.example.feature.material.data.local.MaterialLocalContract
import com.example.feature.material.data.remote.MaterialRemoteContract
import com.example.feature.material.domain.MaterialContract
import com.example.feature.material.domain.MaterialReceiptScanInformationEntity
import com.example.helper.receiptDateFormat
import java.util.*

class MaterialRepository(
    private val localDataSource: MaterialLocalContract,
    private val remoteDataSource: MaterialRemoteContract
) : MaterialContract {

    override suspend fun submitReceiptInformation(
        userId: String,
        warehouseId: String,
        info: MaterialReceiptScanInformationEntity
    ): Result<MaterialReceiptScanInformationEntity, Error> {
        return localDataSource.addReceiptInformation("", "", info)
    }

    override suspend fun generateReceiptNoNumber(): Result<String, Error> {
        val localReceipts = localDataSource.getMaterials().success() ?: emptyList()
        val today = Date().receiptDateFormat()
        val userId = "WN"
        val count = 1 + (localReceipts.firstOrNull()?.receiptNo?.takeLast(3)?.toInt() ?: 0)
        val autoId = String.format("%03d", count)
        return Result.Success("$today$userId$autoId")
    }

    override suspend fun getReceiptDetail(receiptNoNumber: String): Result<MaterialReceiptScanInformationEntity, Error> {
        return localDataSource.getReceiptDetail(receiptNoNumber)
    }
}