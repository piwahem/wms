package com.example.feature


import com.example.base.Error

data class MainViewState(
        val error: Error?,
        val isLoading: Boolean = false,
        val isSignOutSuccess: Boolean = false
)