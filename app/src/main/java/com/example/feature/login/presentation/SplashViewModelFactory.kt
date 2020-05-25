package com.example.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature.login.domain.SignIn
import com.example.feature.login.domain.CheckUserLogin
import com.example.feature.login.domain.GetLoginInformation

class SplashViewModelFactory(
    private val authenticate: SignIn,
    private val isUserLogin: CheckUserLogin,
    private val getLoginInformation: GetLoginInformation
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(
            authenticate,
            isUserLogin,
            getLoginInformation
        ) as T
    }
}