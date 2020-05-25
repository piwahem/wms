package com.example.feature.userinformation.data

import com.example.feature.userinformation.domain.UserInformationEntity

class UserInformationMapper {

//    val mapNetworkToEntities: (SourceNetworkResponse) -> List<SourceDomainEntity> = {
//            response ->
//        mapList(response.sources, mapNetworkToEntity)
//    }
//
//    val mapNetworkToLocals: (SourceNetworkResponse) -> List<SourceLocalDTO> = {
//            response ->
//        mapList(response.sources, mapNetworkToLocal)
//    }

//    val mapLocalToEntities: (List<MaterialLocalDTO>) -> List<MaterialReceiptScanInformationEntity> =
//        {
//            mapList(it, mapLocalToEntity)
//        }
//
//    val mapEntitiesToLocal: (List<SourceDomainEntity>) -> List<SourceLocalDTO> = {
//        mapList(it, mapEntityToLocal)
//    }

    val mapNetworkToEntity: (UserInformationResponse) -> UserInformationEntity = { networkDTO ->
        UserInformationEntity(
            networkDTO.username,
            networkDTO.companyName
        )
    }

    val mapNetworkToLocal: (UserInformationResponse) -> UserInformationDTO = { networkDTO ->
        UserInformationDTO(
            networkDTO.username,
            networkDTO.companyName
        )
    }

    val mapLocalToEntity: (UserInformationDTO) -> UserInformationEntity = { localDTO ->
        UserInformationEntity(
            localDTO.username,
            localDTO.companyName
        )
    }

    val mapEntityToLocal: (UserInformationEntity) -> UserInformationDTO = { entity ->
        UserInformationDTO(
            entity.username,
            entity.companyName
        )
    }
}