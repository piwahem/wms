package com.example.feature.login.presentation


import com.example.base.Error
import com.example.feature.login.domain.AuthenticationEntity

data class LoginViewState(
        val data: AuthenticationEntity?,
        val error: Error?,
        val isLoading: Boolean = false,
        val isLoginSuccess: Boolean = false
)