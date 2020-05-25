package com.example.feature.login.domain

data class AuthenticationEntity(
    var username: String = "",
    var password: String = "",
    var deviceId: String = "",
    var token: String = ""
)