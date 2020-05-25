package com.example.feature.material.presentation.search

import com.example.feature.material.domain.MaterialReceiptScanInformationEntity
import com.example.base.Error

data class MaterialReceiptListViewState(
    val isLoading: Boolean = false,
    val receipts: List<MaterialReceiptScanInformationEntity>? = null,
    val error: Error? = null
)