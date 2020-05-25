package com.example.feature.material.domain

import java.util.*

data class MaterialReceiptScanInformationEntity(
    var receiptNo: String = "",
    var qcScan: Int = 0,
    var device: Int = 0,
    var yourScan: Int = 0,
    var mode: String = "",
    var scan: ScanInformation? = null
) {
    data class ScanInformation(
        var scanId: String = "",
        var dono: String = "",
        var ao: String = "",
        var pono: String = "",
        var ctnQty: Double = 0.0,
        var supplier: String = "",
        var itemCD: String = "",
        var cargoCLSDate: Date = Date(),
        var itemName: String = "",
        var colorWay: String = "",
        var sosOrigin: String = "",
        var packageNo: String = "",
        var numberOfMaterial: Int = 0
    )
}