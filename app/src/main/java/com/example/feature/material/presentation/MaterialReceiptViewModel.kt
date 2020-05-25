package com.example.feature.material.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.extension.getExampleEmptyScanInfo
import com.example.extension.getExampleScanInfo
import com.example.feature.material.data.MaterialMapper
import com.example.feature.material.data.local.MaterialDatabase
import com.example.feature.material.data.local.MaterialLocalDataSource
import com.example.feature.material.domain.GenerateReceiptNoNumber
import com.example.feature.material.domain.GetReceiptDetail
import com.example.feature.material.domain.MaterialReceiptScanInformationEntity
import com.example.feature.material.domain.SubmitReceiptInformation
import kotlinx.coroutines.launch

class MaterialReceiptViewModel(
    private val generateNoNumberUseCase: GenerateReceiptNoNumber,
    private val submitReceiptInformation: SubmitReceiptInformation,
    private val getReceiptDetail: GetReceiptDetail
) : ViewModel() {

    var viewState: MutableLiveData<MaterialReceiptViewState> = MutableLiveData()
    private val db = MaterialDatabase.getInstance(Any())
    private val localDataSource = MaterialLocalDataSource(
        db,
        MaterialMapper()
    )

    init {
        val viewState = MaterialReceiptViewState()
        this.viewState.value = viewState
    }

    fun generateReceiptNoNumber() {
        viewModelScope.launch {
            generateReceiptId()
        }
    }

    private suspend fun generateReceiptId() {
        val result = generateNoNumberUseCase.generate()
        result.handleResult({
            viewState.value = viewState.value?.copy(
                materialReceipt = MaterialReceiptScanInformationEntity(it)
            )
        }, {
        })
    }

    fun getReceiptDetail(receiptNoNumber: String) {
        viewModelScope.launch {
            val result = getReceiptDetail.getDetail(receiptNoNumber)
            result.handleResult({
                viewState.value = viewState.value?.copy(
                    materialReceipt = it
                )
            }, {
            })

        }
    }

    fun boxScanAction() {
        viewModelScope.launch {
            viewState.value = viewState.value?.copy(
                isScanning = !(viewState.value?.isScanning ?: false)
            )
        }
    }

    fun testToReceivePseudoData() {
        viewModelScope.launch {
            val result = generateNoNumberUseCase.generate()
            result.handleResult({
                viewState.value = viewState.value?.copy(
                    pseudoData = getExampleScanInfo(it),
                    isAllowEdit = false,
                    actionGuideFromComputer = R.id.action_mode_scan
                )
            }, {
            })
        }
    }

    fun submitReceiptInformation(pseudoData: MaterialReceiptScanInformationEntity? = viewState.value?.pseudoData) {
        if (pseudoData == null) return

        viewModelScope.launch {
            viewState.value = viewState.value?.copy(
                isLoading = false,
                materialReceipt = pseudoData,
                error = null,
                pseudoData = null,
                isShowOk = true
            )
        }
    }

    fun submitReceiptInformation(numberOfMaterial: Int) {
        viewModelScope.launch {
            val action = viewState.value?.actionGuideFromComputer
            val newData = viewState.value?.materialReceipt?.copy()

            if (action == R.id.action_mode_scan || action == R.id.action_receive_scan) {
                val result = generateNoNumberUseCase.generate()
                result.handleResult({
                    viewState.value = viewState.value?.copy(
                        pseudoData = getExampleEmptyScanInfo(it),
                        isAllowEdit = false,
                        images = emptyList()
                    )
                }, {
                })
            } else {
                submitReceiptInformation.submit(
                    "",
                    "",
                    newData!!
                )
                viewState.value = viewState.value?.copy(
                    isLoading = false,
                    materialReceipt = getExampleEmptyScanInfo(),
                    error = null,
                    pseudoData = null,
                    isShowOk = false,
                    actionGuideFromComputer = R.id.action_mode_scan,
                    images = emptyList()
                )
            }
        }
    }

    fun addImage(image: ImageItem) {
        val currentImages = viewState.value?.images?.toMutableList() ?: mutableListOf()
        currentImages.add(image)
        viewState.value = viewState.value?.copy(
            images = currentImages
        )
    }


    fun toggleEditMode() {
        viewState.value = viewState.value?.copy(
            isAllowEdit = !(viewState.value?.isAllowEdit ?: false)
        )
    }

    fun handleActionGuideFromPC(action: Int) {
        viewState.value = viewState.value?.copy(
            actionGuideFromComputer = action
        )
    }

    override fun onCleared() {
        super.onCleared()
    }
}