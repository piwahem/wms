package com.example.feature.material.data

import com.example.extension.getExampleScanInfo
import com.example.extension.mapList
import com.example.feature.material.data.local.MaterialLocalDTO
import com.example.feature.material.domain.MaterialReceiptScanInformationEntity

class MaterialMapper {

//    val mapNetworkToEntities: (SourceNetworkResponse) -> List<SourceDomainEntity> = {
//            response ->
//        mapList(response.sources, mapNetworkToEntity)
//    }
//
//    val mapNetworkToLocals: (SourceNetworkResponse) -> List<SourceLocalDTO> = {
//            response ->
//        mapList(response.sources, mapNetworkToLocal)
//    }

    val mapLocalToEntities: (List<MaterialLocalDTO>) -> List<MaterialReceiptScanInformationEntity> =
        {
            mapList(it, mapLocalToEntity)
        }
//
//    val mapEntitiesToLocal: (List<SourceDomainEntity>) -> List<SourceLocalDTO> = {
//        mapList(it, mapEntityToLocal)
//    }

//    val mapNetworkToEntity: (SourceNetworkResponse.Source) -> SourceDomainEntity = {
//            networkDTO ->
//        SourceDomainEntity(
//            networkDTO.category,
//            networkDTO.country,
//            networkDTO.language,
//            networkDTO.id,
//            networkDTO.name
//        )
//    }
//
//    val mapNetworkToLocal: (SourceNetworkResponse.Source) -> SourceLocalDTO = {
//            networkDTO ->
//        SourceLocalDTO(
//            networkDTO.category,
//            networkDTO.country,
//            networkDTO.language,
//            networkDTO.id,
//            networkDTO.name
//        )
//    }

    val mapLocalToEntity: (MaterialLocalDTO) -> MaterialReceiptScanInformationEntity = { localDTO ->
        getExampleScanInfo(localDTO.receiptNo, localDTO.numberOfMaterial)
    }

    val mapEntityToLocal: (MaterialReceiptScanInformationEntity) -> MaterialLocalDTO = { entity ->
        MaterialLocalDTO(
            entity.receiptNo,
            entity?.scan?.itemName ?: "",
            entity?.scan?.numberOfMaterial ?: 0
        )
    }
}