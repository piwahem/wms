package com.example.feature.material.presentation

import com.example.feature.material.domain.MaterialReceiptScanInformationEntity
import com.example.base.Error

data class MaterialReceiptViewState(
        val isLoading: Boolean = false,
        val materialReceipt: MaterialReceiptScanInformationEntity? = null,
        val pseudoData: MaterialReceiptScanInformationEntity? = null,
        val error: Error? = null,
        val isScanning: Boolean = false,
        val isAllowEdit: Boolean = false,
        val isShowOk: Boolean = false,
        val actionGuideFromComputer: Int = 0,
        val images: List<ImageItem> = mutableListOf()
)