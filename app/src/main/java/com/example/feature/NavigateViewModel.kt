package com.example.feature

import androidx.lifecycle.ViewModel
import com.example.feature.material.domain.MaterialReceiptScanInformationEntity
import com.example.feature.material.presentation.SlideMenuItem
import com.example.helper.SingleLiveEvent

class NavigateViewModel : ViewModel() {
    var selected = SingleLiveEvent<SlideMenuItem>()
    var materialSelected = SingleLiveEvent<MaterialReceiptScanInformationEntity>()
    var actionGuideFromComputer = SingleLiveEvent<Int>()
}