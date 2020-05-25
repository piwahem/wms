package com.example.feature.material.domain

import com.example.base.BaseUseCase
import com.example.base.Error
import com.example.base.Result

open class GetReceiptDetail(
    private val repository: MaterialContract
) : BaseUseCase<MaterialReceiptScanInformationEntity>() {
    override suspend fun create(data: Map<String, Any>?): Result<MaterialReceiptScanInformationEntity, Error> {
        val receiptNoNumber = (data?.get(KEY_RECEIPT_ID) as? String).orEmpty()
        return repository.getReceiptDetail(receiptNoNumber)
    }

    suspend fun getDetail(
        receiptNoNumber: String
    ): Result<MaterialReceiptScanInformationEntity, Error> {
        val data = HashMap<String, Any>()
        data[KEY_RECEIPT_ID] = receiptNoNumber
        return create(data)
    }

    companion object {
        const val KEY_RECEIPT_ID = "KEY_RECEIPT_ID"
    }
}