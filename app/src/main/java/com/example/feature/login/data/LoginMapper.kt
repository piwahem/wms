package com.example.feature.login.data

import com.example.feature.login.domain.AuthenticationEntity

class LoginMapper {

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

    val mapNetworkToEntity: (AuthenticationResponse) -> AuthenticationEntity = { networkDTO ->
        AuthenticationEntity(
            networkDTO.username,
            networkDTO.password,
            networkDTO.deviceId,
            networkDTO.token
        )
    }

    val mapNetworkToLocal: (AuthenticationResponse) -> AuthenticationDTO = { networkDTO ->
        AuthenticationDTO(
            networkDTO.username,
            networkDTO.password,
            networkDTO.deviceId,
            networkDTO.token
        )
    }

    val mapLocalToEntity: (AuthenticationDTO) -> AuthenticationEntity = { localDTO ->
        AuthenticationEntity(
            localDTO.username,
            localDTO.password,
            localDTO.deviceId,
            localDTO.token
        )
    }

    val mapEntityToLocal: (AuthenticationEntity) -> AuthenticationDTO = { entity ->
        AuthenticationDTO(
            entity.username,
            entity.password,
            entity.deviceId,
            entity.token
        )
    }
}