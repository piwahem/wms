package com.example.feature.material.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "material"
)
data class MaterialLocalDTO(
    @PrimaryKey @ColumnInfo(name = "receiptNo") val receiptNo: String,
    @ColumnInfo(name = "scanInformation") val scanInformation: String,
    @ColumnInfo(name = "numberOfMaterial") val numberOfMaterial: Int
)