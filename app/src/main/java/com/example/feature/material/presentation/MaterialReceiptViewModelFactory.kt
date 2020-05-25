package com.example.feature.material.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature.material.domain.GenerateReceiptNoNumber
import com.example.feature.material.domain.GetReceiptDetail
import com.example.feature.material.domain.SubmitReceiptInformation

class MaterialReceiptViewModelFactory(
    private val generateReceiptNoNumber: GenerateReceiptNoNumber,
    private val submitReceiptInformation: SubmitReceiptInformation,
    private val getReceiptDetail: GetReceiptDetail
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MaterialReceiptViewModel(
            generateReceiptNoNumber,
            submitReceiptInformation,
            getReceiptDetail
        ) as T
    }
}