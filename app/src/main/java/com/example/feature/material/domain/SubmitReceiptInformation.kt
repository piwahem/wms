package com.example.feature.material.domain

import com.example.base.BaseUseCase
import com.example.base.Error
import com.example.base.Result

open class SubmitReceiptInformation(
    private val repository: MaterialContract
) : BaseUseCase<MaterialReceiptScanInformationEntity>() {
    override suspend fun create(data: Map<String, Any>?): Result<MaterialReceiptScanInformationEntity, Error> {
        val userId = (data?.get(KEY_USER_ID) as? String).orEmpty()
        val warehouseId = (data?.get(KEY_WAREHOUSE_ID) as? String).orEmpty()
        val info = (data?.get(KEY_RECEIPT_INFO) as MaterialReceiptScanInformationEntity)
        return repository.submitReceiptInformation(userId, warehouseId, info)
    }

    suspend fun submit(
        userId: String,
        warehouseId: String,
        info: MaterialReceiptScanInformationEntity
    ): Result<MaterialReceiptScanInformationEntity, Error> {
        val data = HashMap<String, Any>()
        data[KEY_RECEIPT_INFO] = info
        data[KEY_USER_ID] = userId
        data[KEY_WAREHOUSE_ID] = warehouseId
        return create(data)
    }

    companion object {
        const val KEY_RECEIPT_INFO = "KEY_RECEIPT_INFO"
        const val KEY_USER_ID = "KEY_USER_ID"
        const val KEY_WAREHOUSE_ID = "KEY_WAREHOUSE_ID"
    }
}