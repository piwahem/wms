package com.example.feature.material.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.feature.MainApplication
import com.example.feature.material.data.local.MaterialDatabase.Companion.MATERIAL_DATABASE_NAME
import com.example.helper.SingletonHolder

@Database(
    entities = [MaterialLocalDTO::class],
    version = 1
)
abstract class MaterialDatabase : RoomDatabase() {
    abstract fun getMaterialDAO(): MaterialLocalDAO

    companion object : SingletonHolder<MaterialDatabase, Any>({
        Room.databaseBuilder(
            MainApplication.appContext,
            MaterialDatabase::class.java,
            MATERIAL_DATABASE_NAME
        ).build()
    }) {
        const val MATERIAL_DATABASE_NAME = "material_db"
    }
}