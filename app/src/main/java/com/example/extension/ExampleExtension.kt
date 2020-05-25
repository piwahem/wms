package com.example.extension

import com.example.feature.material.domain.MaterialReceiptScanInformationEntity
import java.util.*

fun getExampleScanInfo(
    receiptNoNumber: String? = "",
    numberOfMaterial: Int = 1
): MaterialReceiptScanInformationEntity {
    return MaterialReceiptScanInformationEntity(
        receiptNoNumber ?: "",
        457,
        12,
        101,
        "By PO",
        MaterialReceiptScanInformationEntity.ScanInformation(
            getRandomScanId(),
            "DO_20191126_W47_0028",
            "AD-BCA-0040",
            "PN-20191030-3011",
            6.100,
            "SHINWOO VINA CO., LTD",
            "FABBCA0000048",
            Date(),
            " NYLON 330d ROBIC RIPSTOP PD WR PU1500mm OR-18W-2#32 66 BB 58 CUT",
            "Black",
            "KOR",
            "P_TNF-1780_1_TNF1285RGL039001_01",
            numberOfMaterial
        )
    )
}

fun getExampleEmptyScanInfo(receiptNoNumber: String = ""): MaterialReceiptScanInformationEntity {
    return MaterialReceiptScanInformationEntity(
         receiptNoNumber,
        0,
        0,
        0,
        "",
        MaterialReceiptScanInformationEntity.ScanInformation(
            "",
            "",
            "",
            "",
            0.0,
            "",
            "",
            Date(),
            "",
            "",
            "",
            "",
            0
        )
    )
}

fun getExampleReceiptInfo(): MaterialReceiptScanInformationEntity {
    return MaterialReceiptScanInformationEntity(
        " 2002180001-0005",
        457,
        12,
        101,
        "By PO",
        null
    )
}

fun getExampleReceiptList(): List<MaterialReceiptScanInformationEntity> {
    return listOf(
        getExampleScanInfo("2002180001-0001", 1),
        getExampleScanInfo("2002180001-0002", 2),
        getExampleScanInfo("2002180001-0003", 3),
        getExampleScanInfo("2002180001-0004", 4),
        getExampleScanInfo("2002180001-0005", 5),
        getExampleScanInfo("2002180001-0006", 6)
    )
}

fun getRandomScanId(): String = (100..1000).random().toString()