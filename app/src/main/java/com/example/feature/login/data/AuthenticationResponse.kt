package com.example.feature.login.data

data class AuthenticationResponse(
    var username: String = "",
    var password: String = "",
    var deviceId: String = "",
    var token: String = ""
)