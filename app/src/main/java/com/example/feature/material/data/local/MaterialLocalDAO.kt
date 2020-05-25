package com.example.feature.material.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MaterialLocalDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(material: MaterialLocalDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveList(materials: List<MaterialLocalDTO>): List<Long>

    @Query("SELECT * FROM material order by receiptNo desc")
    fun getMaterials(): List<MaterialLocalDTO>

    @Query("SELECT * FROM material where receiptNo =:receiptNoNumber")
    fun getDetail(receiptNoNumber: String): MaterialLocalDTO
}