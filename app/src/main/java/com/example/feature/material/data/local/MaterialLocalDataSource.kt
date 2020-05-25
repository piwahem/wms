package com.example.feature.material.data.local

import androidx.room.withTransaction
import com.example.base.Error
import com.example.base.Result
import com.example.feature.material.data.MaterialMapper
import com.example.feature.material.domain.MaterialReceiptScanInformationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MaterialLocalDataSource(
    private val db: MaterialDatabase,
    private val mapper: MaterialMapper
) : MaterialLocalContract {

    override suspend fun addReceiptInformation(
        userId: String,
        warehouseId: String,
        info: MaterialReceiptScanInformationEntity
    ): Result<MaterialReceiptScanInformationEntity, Error> {
        return withContext(Dispatchers.Main) {
            db.withTransaction {
                val input = mapper.mapEntityToLocal(info)
                val rowId = db.getMaterialDAO().save(input)
                if (rowId > 0) {
                    return@withTransaction Result.Success(
                        mapper.mapLocalToEntity(input)
                    )
                } else {
                    return@withTransaction Result.Failure(
                        Error.RandomError(Throwable("Add data fail...."))
                    )
                }
            }
        }
    }

    override suspend fun getMaterials(): Result<List<MaterialReceiptScanInformationEntity>, Error> {
        return withContext(Dispatchers.Main) {
            db.withTransaction {
                val list = db.getMaterialDAO().getMaterials()
                if (list.isNotEmpty()) {
                    return@withTransaction Result.Success(
                        mapper.mapLocalToEntities(list)
                    )
                } else {
                    return@withTransaction Result.Success(
                        emptyList<MaterialReceiptScanInformationEntity>()
                    )
                }
            }
        }
    }

    override suspend fun getReceiptDetail(receiptNoNumber: String): Result<MaterialReceiptScanInformationEntity, Error> {
        return withContext(Dispatchers.Main) {
            db.withTransaction {
                val item = db.getMaterialDAO().getDetail(receiptNoNumber)
                if (item != null) {
                    return@withTransaction Result.Success(
                        mapper.mapLocalToEntity(item)
                    )
                } else {
                    return@withTransaction Result.Failure(
                        Error.RandomError(Throwable("Get detail fail...."))
                    )
                }
            }
        }
    }
}