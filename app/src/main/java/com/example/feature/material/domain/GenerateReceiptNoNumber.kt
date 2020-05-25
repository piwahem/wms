package com.example.feature.material.domain

import com.example.base.BaseUseCase
import com.example.base.Error
import com.example.base.Result

open class GenerateReceiptNoNumber(
    private val repository: MaterialContract
) : BaseUseCase<String>() {
    override suspend fun create(data: Map<String, Any>?): Result<String, Error> {
        return repository.generateReceiptNoNumber()
    }

    suspend fun generate(): Result<String, Error> {
        return create(null)
    }
}